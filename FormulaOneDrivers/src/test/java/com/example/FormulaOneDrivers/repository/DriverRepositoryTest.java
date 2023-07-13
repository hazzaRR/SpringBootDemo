package com.example.FormulaOneDrivers.repository;

import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.model.Driver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

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


        Driver driver1 = new Driver("Lewis", "Hamilton", LocalDate.of(197, JULY, 21), mercedes);
        Driver driver2 = new Driver("George", "Russell", LocalDate.of(1997, MAY, 13), mercedes);


        underTest.save(driver1);
        underTest.save(driver2);

        //when

        List<Driver> result = underTest.findDriversByConstructor(mercedes.getId());

        //then

        assertThat(result).hasSize(2);

        assertThat(result).extracting(Driver::getName).containsExactlyInAnyOrder("Lewis Hamilton", "George Russell");
    }

}