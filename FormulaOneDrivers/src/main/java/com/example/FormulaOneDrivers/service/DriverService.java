package com.example.FormulaOneDrivers.service;

import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.model.Driver;
import com.example.FormulaOneDrivers.repository.ConstructorRepository;
import com.example.FormulaOneDrivers.repository.DriverRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DriverService {


    private DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
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
}
