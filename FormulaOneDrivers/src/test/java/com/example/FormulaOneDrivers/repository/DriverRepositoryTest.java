package com.example.FormulaOneDrivers.repository;

import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.model.Driver;
import org.apache.tomcat.util.bcel.Const;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.Month.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class DriverRepositoryTest {

    @Autowired
    private DriverRepository underTest;


    @Autowired
    private ConstructorRepository constructorRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void FindByTeam() {
        //given

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        constructorRepository.save(mercedes);


        Driver driver1 = new Driver("Lewis", "Hamilton", 44, LocalDate.of(197, JULY, 21), mercedes);
        Driver driver2 = new Driver("George", "Russell", 63, LocalDate.of(1997, MAY, 13), mercedes);


        underTest.save(driver1);
        underTest.save(driver2);

        //when

        List<Driver> result = underTest.findDriversByConstructor(mercedes.getId());

        //then

        assertThat(result).hasSize(2);

        assertThat(result).extracting(Driver::getName).containsExactlyInAnyOrder("Lewis Hamilton", "George Russell");
    }

    @Test
    void FindByTeamThatDoesNotExist() {
        //given

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        constructorRepository.save(mercedes);

        //when

        List<Driver> result = underTest.findDriversByConstructor(mercedes.getId());

        //then

        assertThat(result).isEmpty();
    }

    @Test
    void FindByRacingNumber() {

        //given

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        constructorRepository.save(mercedes);


        Driver driver1 = new Driver("Lewis", "Hamilton", 44, LocalDate.of(197, JULY, 21), mercedes);

        underTest.save(driver1);

        //when

        Optional<Driver> result = underTest.findByRacingNumber(driver1.getRacingNumber());

        //then

        assertThat(result).contains(driver1);
    }

    @Test
    void FindByRacingNumberThatDoesNotExist() {

        //given

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        constructorRepository.save(mercedes);


        Driver driver1 = new Driver("Lewis", "Hamilton", 44, LocalDate.of(197, JULY, 21), mercedes);

        //when

        Optional<Driver> result = underTest.findByRacingNumber(driver1.getRacingNumber());

        //then

        assertThat(result).isEmpty();
    }


}