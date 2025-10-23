package com.ejemplo.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    @PrePersist
    public void prePersist(){
        System.out.println("Pre Persist");
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        System.out.println("Pre Update");
        this.updatedAt = LocalDateTime.now();
    }
}
