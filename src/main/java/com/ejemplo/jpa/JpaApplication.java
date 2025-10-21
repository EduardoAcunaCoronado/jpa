package com.ejemplo.jpa;

import com.ejemplo.jpa.entities.Person;
import com.ejemplo.jpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JpaApplication implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Person> persons = personRepository.findByProgrammingLanguageAndName("Java", "Josefa");
        persons.forEach(System.out::println);

        List<Object[]> personValues = personRepository.obtenerPersonData("Josefa", "Java");
        personValues.forEach(p -> System.out.println(p[0] + " " + p[1]));
    }
}
