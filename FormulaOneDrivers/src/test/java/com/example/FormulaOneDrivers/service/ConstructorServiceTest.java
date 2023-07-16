package com.example.FormulaOneDrivers.service;

import com.example.FormulaOneDrivers.exceptions.ConstructorCodeAlreadyTakenException;
import com.example.FormulaOneDrivers.exceptions.ConstructorNotFoundException;
import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.model.Driver;
import com.example.FormulaOneDrivers.repository.ConstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ConstructorServiceTest {

    private ConstructorService underTest;

    @Mock
    private ConstructorRepository constructorRepository;

    @BeforeEach
    void setUp() {
        underTest = new ConstructorService(constructorRepository);
    }


    @Test
    void getConstructors() {

        //when
        underTest.getConstructors();

        //then
        verify(constructorRepository).findAll();

    }

    @Test
    void CanAddConstructor() {

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        when(constructorRepository.findByConstructorCode(anyString())).thenReturn(Optional.empty());

        ArgumentCaptor<Constructor> constructorArgumentCaptor = ArgumentCaptor.forClass(Constructor.class);
        underTest.addConstructor(mercedes);

        verify(constructorRepository).save(constructorArgumentCaptor.capture());


        // Verify the captured Driver object
        Constructor capturedConstructor = constructorArgumentCaptor.getValue();
        assertThat(capturedConstructor.getId()).isEqualTo(mercedes.getId());
        assertThat(capturedConstructor.getName()).isEqualTo(mercedes.getName());
        assertThat(capturedConstructor.getConstructorCode()).isEqualTo(mercedes.getConstructorCode());
        assertThat(capturedConstructor.getHeadquarters()).isEqualTo(mercedes.getHeadquarters());


    }

    @Test
    void CannotAddConstructor_CodeTaken() {

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        when(constructorRepository.findByConstructorCode(anyString())).thenReturn(Optional.of(mercedes));

        ConstructorCodeAlreadyTakenException exception = assertThrows(ConstructorCodeAlreadyTakenException.class, () -> {
            underTest.addConstructor(mercedes);
        });

        // Verify interactions
        verify(constructorRepository, times(1)).findByConstructorCode(anyString());
        verify(constructorRepository, never()).save(any(Constructor.class));

        // Verify the exception message
        assertThat(exception.getMessage()).isEqualTo("Constructor Code already in use");



    }

    @Test
    void CanDeleteConstructor() {

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        when(constructorRepository.findById(anyLong())).thenReturn(Optional.of(mercedes));

        underTest.deleteConstructor(anyLong());

        verify(constructorRepository, times(1)).findById(anyLong());
        verify(constructorRepository, times(1)).deleteById(anyLong());
    }

    void CanNotDeleteConstructor() {

        Constructor mercedes = new Constructor("MERC", "Mercedes", "UK");

        when(constructorRepository.findById(anyLong())).thenReturn(Optional.empty());

        ConstructorNotFoundException exception = assertThrows(ConstructorNotFoundException.class, () -> {
            underTest.deleteConstructor(anyLong());
        });

        verify(constructorRepository, times(1)).findById(anyLong());
        verify(constructorRepository, never()).deleteById(anyLong());

        assertThat(exception.getMessage()).isEqualTo("No constructor with that id");
    }

    @Test
    void updateConstructor() {
    }

    @Test
    void getConstructorByCode() {

        //when
        underTest.getConstructorByCode(anyString());

        //then
        verify(constructorRepository).findByConstructorCode(anyString());
    }

    @Test
    void getConstructorsByHeadquarters() {

        //when
        underTest.getConstructorsByHeadquarters(anyString());

        //then
        verify(constructorRepository).findByHeadquarters(anyString());
    }
}