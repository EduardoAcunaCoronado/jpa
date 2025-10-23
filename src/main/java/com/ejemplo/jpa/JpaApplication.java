package com.ejemplo.jpa;

import com.ejemplo.jpa.dto.PersonDTO;
import com.ejemplo.jpa.entities.Person;
import com.ejemplo.jpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        personalizedQueriesBetween();
    }

    public void personalizedQueriesBetween() {
        System.out.println("--- BETWEEN ID---");
        List<Person> personList = personRepository.findAllBetweenId(2L, 5L);
        personList.forEach(System.out::println);
        System.out.println("--- BETWEEN NAME ---");
        personList = personRepository.findAllBetweenName("E", "S");
        personList.forEach(System.out::println);

        System.out.println("--- BETWEEN ID QUERY METHOD ---");
        personList = personRepository.findByIdBetween(2L, 5L);
        personList.forEach(System.out::println);
        System.out.println("--- BETWEEN NAME QUERY METHOD ---");
        personList = personRepository.findByNameBetween("E", "S");
        personList.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void personalizedQueriesConcatUpperAndLowerCase() {
        System.out.println("--- Consulta nombres y apellidos de persona");
        List<String> names = personRepository.findAllFullnameConcat();
        names.forEach(System.out::println);
        System.out.println("--- Upper nombres y apellidos de persona");
        List<String> namesUpper = personRepository.findAllFullnameConcatUpper();
        namesUpper.forEach(System.out::println);
        System.out.println("--- Lower nombres y apellidos de persona");
        List<String> namesLower = personRepository.findAllFullnameConcatLower();
        namesLower.forEach(System.out::println);
        System.out.println("--- Mezcla nombres y apellidos de persona");
        List<Object[]> mezcla = personRepository.findAllPersonDataListCase();
        mezcla.forEach(item -> System.out.println(item[0].toString() + item[1].toString() + item[2].toString() + item[3].toString()));
    }

    @Transactional(readOnly = true)
    public void personalizedQueriesDistinct() {
        System.out.println("--- Consulta con nombres de personas ---");
        List<String> names = personRepository.findAllNames();
        names.forEach(System.out::println);
        System.out.println("--- Consulta con nombres de personas distintas ---");
        List<String> namesDistinct = personRepository.findAllNamesDistinct();
        namesDistinct.forEach(System.out::println);
        System.out.println("--- Contar distintos lenguajes de programación ---");
        Long programmingLanguagesDistinctCount = personRepository.findAllProgrammingLanguagesDistinctCount();
        System.out.println(programmingLanguagesDistinctCount);
    }

    public void personalizedQueries2() {
        System.out.println("----- PERSONALIZED QUERIES2 -----");
        List<Object[]> list = personRepository.findAllMixPerson();
        list.forEach(item -> System.out.println(item[0].toString() + item[1].toString()));
        System.out.println("----- PERSONALIZED QUERIES3 -----");
        List<Person> list2 = personRepository.findAllObjectPersonalizedPerson();
        list2.forEach(System.out::println);
        System.out.println("----- PERSONALIZED QUERIES4 -----");
        List<PersonDTO> list3 = personRepository.findAllPersonDTO();
        list3.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void personalizedQueries() {
        Scanner sc = new Scanner(System.in);

        System.out.println("===================== Consulta por id =====================");
        System.out.println("Introduce el id del personalized query");
        Long id = sc.nextLong();

        Long idDb = personRepository.getIdById(id);
        System.out.println(idDb);

        String name = personRepository.getNameById(id);
        System.out.println(name);

        String fullname = personRepository.getFullnameById(id);
        System.out.println(fullname);

        System.out.println("===================== Consulta los campos personalizados por el id =====================");
        Object[] personReg = (Object[]) personRepository.obtenerPersonDataById(id);
        System.out.println("id:" + personReg[0]);
        System.out.println("name:" + personReg[1]);
        System.out.println("lastname:" + personReg[2]);
        System.out.println("programmingLanguage:" + personReg[3]);

        System.out.println("===================== Consulta los campos personalizados =====================");
        List<Object[]> personList = personRepository.obtenerPersonDataList();

        personList.forEach(item -> System.out.println(item[0].toString() + item[1].toString() + item[2].toString() + item[3].toString()));

        sc.close();
    }

    @Transactional
    public void delete2() {
        personRepository.findAll().forEach(System.out::println);
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el id del person");
        Long id = sc.nextLong();

        Optional<Person> person = personRepository.findById(id);
        person.ifPresentOrElse(
                personRepository::delete,
                () -> System.out.println("No existe")
        );

        personRepository.findAll().forEach(System.out::println);
        sc.close();
    }

    @Transactional
    public void delete() {
        personRepository.findAll().forEach(System.out::println);
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el id del person");
        Long id = sc.nextLong();
        personRepository.deleteById(id);
        personRepository.findAll().forEach(System.out::println);
        sc.close();
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
