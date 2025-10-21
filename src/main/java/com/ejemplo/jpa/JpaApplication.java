package com.ejemplo.jpa;

import com.ejemplo.jpa.entities.Person;
import com.ejemplo.jpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class JpaApplication implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        update();
    }

    @Transactional
    public void update() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el id del person:");
        Long id = sc.nextLong();
        Optional<Person> optionalPerson = personRepository.findById(id);
        optionalPerson.ifPresent(person -> {
            System.out.println("Introduce el lenguaje de programación del person:");
            String programmingLanguage = sc.next();
            person.setProgrammingLanguage(programmingLanguage);
            Person personDb = personRepository.save(person);
            System.out.println(personDb);
        });
        sc.close();
    }

    @Transactional
    public void create() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el nombre del person:");
        String name = sc.nextLine();
        System.out.println("Introduce el apellido del person:");
        String lastname = sc.nextLine();
        System.out.println("Introduce el lenguaje de programación del person:");
        String programmingLanguage = sc.nextLine();
        sc.close();

        Person person = new Person(null, name, lastname, programmingLanguage);

        Person personNew = personRepository.save(person);
        personRepository.findById(personNew.getId()).ifPresent(System.out::println);
    }

    @Transactional(readOnly = true)
    public void findOne() {
//        Person person = null;
//        Optional<Person> personOptional = personRepository.findById(8L);
//        if (personOptional.isPresent()) {
//            person = personOptional.get();
//        }
//        System.out.println(person);

        personRepository.findById(5L).ifPresent(System.out::println);
        personRepository.findOneById(5L).ifPresent(System.out::println);
        personRepository.findOneByName("Josefa").ifPresent(System.out::println);
        personRepository.findOneLikeName("ose").ifPresent(System.out::println);
        personRepository.findByNameContaining("ose").ifPresent(System.out::println);

    }

    @Transactional(readOnly = true)
    public void list() {
        List<Person> persons = personRepository.findByProgrammingLanguageAndName("Java", "Josefa");
        persons.forEach(System.out::println);

        List<Object[]> personValues = personRepository.obtenerPersonData("Josefa", "Java");
        personValues.forEach(p -> System.out.println(p[0] + " " + p[1]));
    }
}
