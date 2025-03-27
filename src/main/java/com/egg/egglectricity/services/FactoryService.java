package com.egg.egglectricity.services;
import com.egg.egglectricity.entities.Factory;
import com.egg.egglectricity.exceptions.InvalidInputException;
import com.egg.egglectricity.exceptions.ResourceNotFoundException;
import com.egg.egglectricity.repositories.FactoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactoryService {

    @Autowired
    FactoryRepository factoryRepository;

    @Transactional
    public Factory create(String name) throws InvalidInputException {
        validateData(name);
        Factory factory = new Factory();
        factory.setName(name);
        factoryRepository.save(factory);
        return factory;
    }
    @Transactional
    public Factory update(Long id, String name) throws ResourceNotFoundException, InvalidInputException {
        //Check if the factory exists
        Factory factory = findById(id);
        validateData(name);
        factory.setName(name); // modify the factory
        return factoryRepository.save(factory);
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Factory factory = findById(id);
        factoryRepository.delete(factory);
    }

    public List<Factory> list() {
        return factoryRepository.findAll();
    }

    public Factory findById(Long id) throws ResourceNotFoundException {
        Optional<Factory> factoryResponse = factoryRepository.findById(id);
        if(factoryResponse.isPresent()){
            return factoryResponse.get();
        }
        throw new ResourceNotFoundException("Factory with id "+ id +" not found");
    }

    private void validateData(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Factory name cannot be null or empty.");
        }
    }
}
