package com.example.FormulaOneDrivers.service;

import com.example.FormulaOneDrivers.model.Constructor;
import com.example.FormulaOneDrivers.repository.ConstructorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConstructorService {

    private ConstructorRepository constructorRepository;

    public ConstructorService(ConstructorRepository constructorRepository) {
        this.constructorRepository = constructorRepository;
    }
    public List<Constructor> getConstructors() {
        return constructorRepository.findAll();
    }

    public void addConstructor(Constructor constructor) {

        Optional<Constructor> constructorOptional = constructorRepository.findByConstructorCode(constructor.getConstructorCode());
        if (constructorOptional.isPresent()) {
            throw new IllegalStateException("Constructor Code already in use");
        }
        constructorRepository.save(constructor);
    }

    public void deleteConstructor(long id) {

        Optional<Constructor> constructorById = constructorRepository.findById(id);

        if (constructorById.isEmpty()) {
            throw new IllegalStateException("No constructor with that id");
        }
        constructorRepository.deleteById(id);
    }

    @Transactional
    public void updateConstructor(long id, String name, String constructorCode, String headquarters) {

        Constructor constructor = constructorRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "constructor with id " + id + " does not exist"
        ));

        if (name != null && name.length() > 0 && !Objects.equals(constructor.getName(), name)) {
            constructor.setName(name);
        }

        if (constructorCode != null && constructorCode.length() > 0 && !Objects.equals(constructor.getConstructorCode(), constructorCode)) {

            Optional<Constructor> constructorOptional = constructorRepository.findByConstructorCode(constructorCode);
            if (constructorOptional.isPresent()) {
                throw new IllegalStateException("Constructor Code already in use");
            }
            constructor.setConstructorCode(constructorCode);
        }

        if (headquarters != null && headquarters.length() > 0 && !Objects.equals(constructor.getHeadquarters(), headquarters)) {
            constructor.setHeadquarters(headquarters);
        }

    }

    public Optional<Constructor> getConstructorByCode(String code) {
        return constructorRepository.findByConstructorCode(code);
    }

    public List<Constructor> getConstructorsByHeadquarters(String headquarters) {
        return constructorRepository.findByHeadquarters(headquarters);
    }
}
