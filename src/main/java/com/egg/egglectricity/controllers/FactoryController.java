package com.egg.egglectricity.controllers;

import com.egg.egglectricity.entities.Factory;
import com.egg.egglectricity.exceptions.InvalidInputException;
import com.egg.egglectricity.services.FactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/factories")
public class FactoryController {

    private static final Logger logger = LoggerFactory.getLogger(FactoryController.class);

    @Autowired
    FactoryService factoryService;

    @GetMapping("/list")
    public String list(ModelMap model){
        try{
            List<Factory> factories = factoryService.list();
            model.put("factories", factories);
            model.put("success", "Factories successfully retrieved");
        } catch (Exception e) {
            logger.error("Error retrieving factories", e);
            model.put("error", e.getMessage());
        }
        return "factories-list.html";
    }

    @GetMapping("/add")
    public String addForm(){
        return "factory-form.html";
    }

    @PostMapping("/add")
    public String addFactory(@RequestParam String name, ModelMap model){
        try{
            Factory factory = factoryService.create(name);
            model.put("success", "Factory successfully created");
            return "factory-form.html";
        } catch (InvalidInputException e) {
            logger.error("Error creating factory", e.getMessage());
            model.put("error", e.getMessage());
        }

        return "factories-list.html";
    }
}
