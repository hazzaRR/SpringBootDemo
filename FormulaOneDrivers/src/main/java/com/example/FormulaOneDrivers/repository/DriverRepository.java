package com.example.FormulaOneDrivers.repository;

import com.example.FormulaOneDrivers.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(value = "SELECT d.* FROM Driver d INNER JOIN Constructor c ON c.id = d.team_id WHERE d.team_id = ?1", nativeQuery = true)
    List<Driver> findDriversByConstructor(Long constructorId);

}
