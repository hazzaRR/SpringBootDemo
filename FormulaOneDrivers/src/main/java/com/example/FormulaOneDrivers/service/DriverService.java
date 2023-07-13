package com.example.FormulaOneDrivers.service;

import com.example.FormulaOneDrivers.dto.DriverDTO;
import com.example.FormulaOneDrivers.exceptions.ConstructorNotFoundException;
import com.example.FormulaOneDrivers.exceptions.RacingNumberAlreadyTakenException;
import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.model.Driver;
import com.example.FormulaOneDrivers.repository.ConstructorRepository;
import com.example.FormulaOneDrivers.repository.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DriverService {


    private DriverRepository driverRepository;
    private ConstructorRepository constructorRepository;

    public DriverService(DriverRepository driverRepository, ConstructorRepository constructorRepository) {
        this.driverRepository = driverRepository;
        this.constructorRepository = constructorRepository;
    }
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public List<Driver> getDriversFromTeam(long constructorID) {
        return driverRepository.findDriversByConstructor(constructorID);
    }

    public void deleteDriver(long driverID) {

        Optional<Driver> driverById = driverRepository.findById(driverID);

        if (driverById.isEmpty()) {
            throw new IllegalStateException("No driver with that id");
        }
        driverRepository.deleteById(driverID);
    }
@Transactional
    public void updateDriver(long driverID, String firstname, String surname, Integer racingNumber, Constructor team) {

    Driver driver = driverRepository.findById(driverID).orElseThrow(() -> new IllegalStateException(
            "driver with id " + driverID + " does not exist"
    ));

    if (firstname != null && firstname.length() > 0 && !Objects.equals(driver.getFirstname(), firstname)) {
        driver.setFirstname(firstname);
    }

    if (surname != null && surname.length() > 0 && !Objects.equals(driver.getSurname(), surname)) {
        driver.setSurname(surname);
    }

    if (racingNumber != null && !Objects.equals(driver.getRacingNumber(), racingNumber)) {
        Optional<Driver> driverOptional = driverRepository.findByRacingNumber(racingNumber);
        if (driverOptional.isPresent()) {
            throw new IllegalStateException("Racing Number is already taken");
        }
        driver.setRacingNumber(racingNumber);
    }
    if (team != null && !Objects.equals(driver.getTeam(), team)) {
        driver.setTeam(team);
    }

    }

    public void addDriver(DriverDTO driverDTO) {

        Constructor constructor = constructorRepository.findById(driverDTO.getTeamId())
                .orElseThrow(() -> new ConstructorNotFoundException("Constructor does not exist"));

        Optional<Driver> driverOptional = driverRepository.findByRacingNumber(driverDTO.getRacingNumber());
        if (driverOptional.isPresent()) {
            throw new RacingNumberAlreadyTakenException("Racing Number is already taken");
        }

        Driver newDriver = new Driver(driverDTO.getFirstname(), driverDTO.getSurname(), driverDTO.getRacingNumber(), LocalDate.parse(driverDTO.getDob()), constructor);

        driverRepository.save(newDriver);
    }


}
