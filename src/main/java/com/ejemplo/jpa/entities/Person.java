package com.ejemplo.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="persons")
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastname;

    @Column(name = "programming_language")
    private String programmingLanguage;

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }
}
