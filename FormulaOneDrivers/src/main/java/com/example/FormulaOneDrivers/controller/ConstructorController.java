package com.example.FormulaOneDrivers.controller;

import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.service.ConstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/constructor")
public class ConstructorController {

    private final ConstructorService constructorService;


    public ConstructorController(ConstructorService constructorService) {
        this.constructorService = constructorService;
    }


    @GetMapping
    public List<Constructor> getConstructors() {
        return constructorService.getConstructors();
    }

    @GetMapping(params = "headquarters")
    public List<Constructor> getConstructorsByHeadquarters(@RequestParam String headquarters) {
        return constructorService.getConstructorsByHeadquarters(headquarters);
    }

    @GetMapping(path = "code")
    public Optional<Constructor> getConstructorByCode(@RequestParam String code) {
        return constructorService.getConstructorByCode(code);
    }

    @PostMapping
    public void addNewConstructor(@RequestBody Constructor constructor) {
        constructorService.addConstructor(constructor);
    }

    @DeleteMapping(params = "id")
    public void deleteConstructor(@RequestParam long id) {
        constructorService.deleteConstructor(id);
    }

    @PutMapping(path="{constructorID}")
    public void updateConstructor(@PathVariable("constructorID") long id, @RequestParam(required = false) String name, @RequestParam(required = false) String constructor_code, @RequestParam(required = false) String headquarters) {
        constructorService.updateConstructor(id, name, constructor_code, headquarters);
    }

}
