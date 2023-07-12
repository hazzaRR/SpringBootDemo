package com.example.FormulaOneDrivers.repository;

import com.example.FormulaOneDrivers.model.Constructor;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ConstructorRepositoryTest {

    @Autowired
    private ConstructorRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }
    @Test
    void FindByConstructorCode() {
    //given
    Constructor constructor = new Constructor("RBR", "Red Bull Racing", "UK");
    underTest.save(constructor);
    //when

    Optional<Constructor> result = underTest.findByConstructorCode(constructor.getConstructorCode());

    //then

    assertThat(result).contains(constructor);

    }

    @Test
    void FindByConstructorCodeWhichDoesNotExist() {

        //given
        String code = "RBR";


        Optional<Constructor> result = underTest.findByConstructorCode(code);

        //then

        assertThat(result).isEmpty();


    }

    @Test

    void FindByHeadquarters() {

        //given

        Constructor constructor1 = new Constructor("MERC", "Mercedes", "UK");
        Constructor constructor2 = new Constructor("RBR", "Red Bull Racing", "UK");
        Constructor constructor3 = new Constructor("FERR", "Ferrari", "IT");

        underTest.save(constructor1);
        underTest.save(constructor2);
        underTest.save(constructor3);

        //when

        List<Constructor> result = underTest.findByHeadquarters(constructor1.getHeadquarters());

        //then

        List<Constructor> ukConstructors = Arrays.asList(constructor1, constructor2);

        assertThat(result).isEqualTo(ukConstructors);

    }

    @Test
    void FindByHeadquartersWhichDoesNotExist() {

        //given

        String location = "UK";

        //when

        List<Constructor> result = underTest.findByHeadquarters(location);

        //then

        assertThat(result).isEmpty();

    }

}