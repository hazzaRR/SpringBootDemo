package com.example.FormulaOneDrivers.repository;

import com.example.FormulaOneDrivers.model.Constructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConstructorRepository extends JpaRepository<Constructor, Long> {


    Optional<Constructor> findByConstructorCode(String constructorCode);
}
