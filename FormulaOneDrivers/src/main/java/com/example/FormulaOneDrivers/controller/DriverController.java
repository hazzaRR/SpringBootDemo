package com.example.FormulaOneDrivers.controller;

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

}
