package com.haulmont.testtask.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacient")
@NoArgsConstructor
public class Pacient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

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

    @Column(name = "phone")
    @Getter
    @Setter
    private Integer phone;

    @OneToMany(mappedBy = "pacient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Recipe> recipes;

    public Pacient(String firstName, String  lastName, String patronymic, Integer phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phone = phone;
        recipes = new ArrayList<>();
    }

}
