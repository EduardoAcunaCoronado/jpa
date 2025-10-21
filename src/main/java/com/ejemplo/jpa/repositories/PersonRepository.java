package com.ejemplo.jpa.repositories;

import com.ejemplo.jpa.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
