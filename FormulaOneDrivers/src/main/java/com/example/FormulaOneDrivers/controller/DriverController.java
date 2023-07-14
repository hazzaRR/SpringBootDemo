package com.example.FormulaOneDrivers.controller;

import com.example.FormulaOneDrivers.dto.DriverDTO;
import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.model.Driver;
import com.example.FormulaOneDrivers.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/driver")

public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }


    @GetMapping
    public List<Driver> getDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/api/v1/driver/{teamId}") // Mapping for getDriversFromTeam method
    public List<Driver> getDriversFromTeam(@PathVariable long teamId) {
        return driverService.getDriversFromTeam(teamId);
    }

    @DeleteMapping(path="{driverID}")
    public void deleteDriver(@PathVariable("driverID") long driverID) {
        driverService.deleteDriver(driverID);
    }

    @PutMapping(path="{driverID}")
    public void updateDriver(@PathVariable("driverID") long driverID, @RequestParam(required = false) String firstname, @RequestParam(required = false) String surname, @RequestParam(required = false) Integer racingNumber, @RequestParam(required = false) Constructor team) {
        driverService.updateDriver(driverID, firstname, surname, racingNumber, team);
    }

    @PostMapping
    public void addDriver(@RequestBody DriverDTO driverDTO) {
        driverService.addDriver(driverDTO);
    }
}
