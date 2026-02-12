package com.hybernate_jpa.hybernate_jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hybernate_jpa.hybernate_jpa.entities.Persona;
import com.hybernate_jpa.hybernate_jpa.repositories.PersonaDAO;

@SpringBootApplication
public class HybernateJpaApplication implements CommandLineRunner{

	
	@Autowired
	private PersonaDAO personaDAO;

	public static void main(String[] args) {
		SpringApplication.run(HybernateJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Persona> personas = (List<Persona>) personaDAO.findAll();
		personas.stream().forEach(System.out::println);
	}

}
