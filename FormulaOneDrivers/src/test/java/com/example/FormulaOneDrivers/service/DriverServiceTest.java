package com.example.FormulaOneDrivers.service;

import com.example.FormulaOneDrivers.dto.DriverDTO;
import com.example.FormulaOneDrivers.exceptions.ConstructorNotFoundException;
import com.example.FormulaOneDrivers.exceptions.DriverNotFoundException;
import com.example.FormulaOneDrivers.exceptions.RacingNumberAlreadyTakenException;
import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.model.Driver;
import com.example.FormulaOneDrivers.repository.ConstructorRepository;
import com.example.FormulaOneDrivers.repository.DriverRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //this does the same as the commented out sections
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;
    //    private AutoCloseable autoCloseable;
    private DriverService underTest;

    @Mock
    private ConstructorRepository constructorRepository;

    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new DriverService(driverRepository, constructorRepository);
    }

//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    void canGetAllDrivers() {
        //when
        underTest.getAllDrivers();
        //then
        verify(driverRepository).findAll();
    }

    @Test
    void getDriversFromTeam() {

        underTest.getDriversFromTeam(anyLong());

        //then
        verify(driverRepository).findDriversByConstructor(anyLong());
    }

    @Test
    void CanDeleteDriver() {

        long id = 2;

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        when(driverRepository.findById(id))
                .thenReturn(Optional.of(new Driver("Lewis", "Hamilton", 44, LocalDate.of(1988, MAY, 16), mercedes)));

        underTest.deleteDriver(id);

        //when
        //then

        verify(driverRepository).deleteById(id);
    }
    @Test
    void CannotDeleteDriver() {

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Call the method and assert the exception
        DriverNotFoundException exception = assertThrows(DriverNotFoundException.class, () -> {
            underTest.deleteDriver(anyLong());
        });

        // Verify interactions
        verify(driverRepository, times(1)).findById(anyLong());
        verify(driverRepository, never()).deleteById(anyLong());

        // Verify the exception message
        assertThat(exception.getMessage()).isEqualTo("No driver exists with that id");

    }

    @Test
    public void testUpdateDriver_UpdateFirstName() {
        // Arrange
        long driverID = 1;
        String newFirstName = "John";
        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");
        Driver existingDriver = new Driver("Lewis", "Hamilton", 44, LocalDate.of(1988, MAY, 16), mercedes);
        existingDriver.setFirstname("Jane");
        when(driverRepository.findById(driverID)).thenReturn(Optional.of(existingDriver));

        // Act
        underTest.updateDriver(driverID, newFirstName, null, null, null);

        // Assert
        assertThat(existingDriver.getFirstname()).isEqualTo(newFirstName);
    }

    @Test
    public void testUpdateDriver_UpdateSurname() {
        // Arrange
        long driverID = 1;
        String newSurname = "Doe";
        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");
        Driver existingDriver = new Driver("Lewis", "Hamilton", 44, LocalDate.of(1988, MAY, 16), mercedes);
        existingDriver.setSurname("Smith");
        when(driverRepository.findById(driverID)).thenReturn(Optional.of(existingDriver));

        // Act
        underTest.updateDriver(driverID, null, newSurname, null, null);

        // Assert
        assertThat(existingDriver.getSurname()).isEqualTo(newSurname);
    }

    @Test
    public void testUpdateDriver_UpdateRacingNumber() {
        // Arrange
        long driverID = 1;
        int newRacingNumber = 10;
        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");
        Driver existingDriver = new Driver("Lewis", "Hamilton", 44, LocalDate.of(1988, MAY, 16), mercedes);
        existingDriver.setRacingNumber(5);
        when(driverRepository.findById(driverID)).thenReturn(Optional.of(existingDriver));
        when(driverRepository.findByRacingNumber(newRacingNumber)).thenReturn(Optional.empty());

        // Act
        underTest.updateDriver(driverID, null, null, newRacingNumber, null);

        // Assert
        assertThat(existingDriver.getRacingNumber()).isEqualTo(newRacingNumber);
    }

    @Test
    public void testUpdateDriver_RacingNumberAlreadyTaken() {
        // Arrange
        long driverID = 1;
        int newRacingNumber = 10;
        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");
        Driver existingDriver = new Driver("Lewis", "Hamilton", 44, LocalDate.of(1988, MAY, 16), mercedes);
        existingDriver.setRacingNumber(5);
        when(driverRepository.findById(driverID)).thenReturn(Optional.of(existingDriver));
        when(driverRepository.findByRacingNumber(newRacingNumber)).thenReturn(Optional.of(existingDriver));

        // Call the method and assert the exception
        RacingNumberAlreadyTakenException exception = assertThrows(RacingNumberAlreadyTakenException.class, () -> {
            underTest.updateDriver(driverID, null, null, newRacingNumber, null);
        });

        assertThat(exception.getMessage()).isEqualTo("Racing Number is already taken");

    }

    @Test
    public void testUpdateDriver_UpdateTeam() {
        // Arrange
        long driverID = 1;
        Constructor newTeam = new Constructor("FERR", "Ferrari", "IT");
        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");
        Driver existingDriver = new Driver("Lewis", "Hamilton", 44, LocalDate.of(1988, MAY, 16), mercedes);
        existingDriver.setTeam(null);
        when(driverRepository.findById(driverID)).thenReturn(Optional.of(existingDriver));

        // when
        underTest.updateDriver(driverID, null, null, null, newTeam);

        // then
        assertThat(existingDriver.getTeam()).isEqualTo(newTeam);
    }



    @Test
    void CanAddNewDriver() {

        // Mock input data
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setTeamId(1L);
        driverDTO.setRacingNumber(10);
        driverDTO.setFirstname("John");
        driverDTO.setSurname("Doe");
        driverDTO.setDob("1990-01-01");

        // Mock repositories
        Constructor constructor = new Constructor("MERC", "Mercedes", "UK");
        when(constructorRepository.findById(driverDTO.getTeamId())).thenReturn(Optional.of(constructor));
        when(driverRepository.findByRacingNumber(driverDTO.getRacingNumber())).thenReturn(Optional.empty());

        // Call the method
        underTest.addDriver(driverDTO);

        ArgumentCaptor<Driver> driverArgumentCaptor = ArgumentCaptor.forClass(Driver.class);

        // Verify interactions
        verify(constructorRepository, times(1)).findById(driverDTO.getTeamId());
        verify(driverRepository, times(1)).findByRacingNumber(driverDTO.getRacingNumber());
        verify(driverRepository, times(1)).save(driverArgumentCaptor.capture());

        // Verify the captured Driver object
        Driver capturedDriver = driverArgumentCaptor.getValue();
        assertThat(capturedDriver.getFirstname()).isEqualTo(driverDTO.getFirstname());
        assertThat(capturedDriver.getSurname()).isEqualTo(driverDTO.getSurname());
        assertThat(capturedDriver.getRacingNumber()).isEqualTo(driverDTO.getRacingNumber());
        assertThat(capturedDriver.getDob()).isEqualTo(LocalDate.parse(driverDTO.getDob()));
        assertThat(capturedDriver.getTeam()).isEqualTo(constructor);
    }

    @Test
    public void testAddDriverConstructorNotFound() {
        // Mock input data
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setTeamId(1L);

        // Mock repositories
        when(constructorRepository.findById(driverDTO.getTeamId())).thenReturn(Optional.empty());

        // Call the method and assert the exception
        ConstructorNotFoundException exception = assertThrows(ConstructorNotFoundException.class, () -> {
            underTest.addDriver(driverDTO);
        });

        // Verify interactions
        verify(constructorRepository, times(1)).findById(driverDTO.getTeamId());
        verify(driverRepository, never()).findByRacingNumber(anyInt());
        verify(driverRepository, never()).save(any(Driver.class));

        // Verify the exception message
        assertThat(exception.getMessage()).isEqualTo("Constructor does not exist");
    }

    @Test
    public void testAddDriver_RacingNumberAlreadyTaken() {
        // Mock input data
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setRacingNumber(10);

        Constructor redBull = new Constructor("RBR", "Red Bull Racing", "UK");

        when(constructorRepository.findById(driverDTO.getTeamId())).thenReturn(Optional.of(redBull));

        // Mock repositories
        when(driverRepository.findByRacingNumber(driverDTO.getRacingNumber())).thenReturn(Optional.of(new Driver("Sergio", "Perez", 10, LocalDate.of(1989, AUGUST, 23), redBull)));

        // Call the method and assert the exception
        RacingNumberAlreadyTakenException exception = assertThrows(RacingNumberAlreadyTakenException.class, () -> {
            underTest.addDriver(driverDTO);
        });

        // Verify interactions
        verify(constructorRepository, times(1)).findById(anyLong());
        verify(driverRepository, times(1)).findByRacingNumber(driverDTO.getRacingNumber());
        verify(driverRepository, never()).save(any(Driver.class));

        // Verify the exception message
        assertThat(exception.getMessage()).isEqualTo("Racing Number is already taken");
    }
}