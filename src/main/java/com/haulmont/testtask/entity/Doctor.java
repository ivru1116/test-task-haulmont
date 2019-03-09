package com.haulmont.testtask.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Column(name = "firstname", nullable = false)
    @Getter
    @Setter
    private String firstName;

    @Column(name = "surname", nullable = false)
    @Getter
    @Setter
    private String lastName;

    @Column(name = "patronymic", nullable = false)
    @Getter
    @Setter
    private String patronymic;

    @Column(name = "specialization", nullable = false)
    @Getter
    @Setter
    private String specialization;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<Recipe> recipes;

    public Doctor(String firstName, String lastName, String patronymic, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.specialization = specialization;
        recipes = new ArrayList<>();
    }
}
