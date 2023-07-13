package com.example.FormulaOneDrivers.repository;

import com.example.FormulaOneDrivers.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(value = "SELECT * FROM Driver d WHERE d.team_id = ?1", nativeQuery = true)
    List<Driver> findDriversByConstructor(Long constructorId);

    @Query(value = "SELECT * FROM Driver d WHERE racing_number = ?1", nativeQuery = true)
    Optional<Driver> findByRacingNumber(Integer racingNumber);
}
