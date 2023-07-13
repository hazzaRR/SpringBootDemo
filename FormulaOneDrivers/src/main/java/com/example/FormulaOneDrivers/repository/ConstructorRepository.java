package com.example.FormulaOneDrivers.repository;

import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConstructorRepository extends JpaRepository<Constructor, Long> {


    Optional<Constructor> findByConstructorCode(String constructorCode);

    List<Constructor> findByHeadquarters(String headquarters);

}
