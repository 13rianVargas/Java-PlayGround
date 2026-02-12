package com.hybernate_jpa.hybernate_jpa.repositories;
import com.hybernate_jpa.hybernate_jpa.entities.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaDAO extends CrudRepository<Persona, Long> {

    

}
