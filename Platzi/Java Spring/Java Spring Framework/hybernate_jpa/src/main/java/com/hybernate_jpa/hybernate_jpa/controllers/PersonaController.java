package com.hybernate_jpa.hybernate_jpa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hybernate_jpa.hybernate_jpa.entities.Persona;
import com.hybernate_jpa.hybernate_jpa.repositories.PersonaDAO;


@RestController
@RequestMapping("/api/personas")
public class PersonaController {
    @Autowired
    private PersonaDAO personaDAO;
    
    @GetMapping("/hola")
    public List<Persona> getPersonas() {
        return (List<Persona>) personaDAO.findAll();
    }
    
}
