package com.example.FormulaOneDrivers.repository;

import com.example.FormulaOneDrivers.model.Constructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

}