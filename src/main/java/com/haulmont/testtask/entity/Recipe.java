package com.haulmont.testtask.entity;


import com.haulmont.testtask.enums.RecipePriority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "recipe")
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column(name = "description", nullable = false)
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "creation", nullable = false)
    @Getter
    @Setter
    private Date creation;

    @Column(name = "exposure", nullable = false)
    @Getter
    @Setter
    private Date exposure;

    @Column(name = "doctor", nullable = false)
    @Getter
    @Setter
    private String doctors;

    @Column(name = "pacient", nullable = false)
    @Getter
    @Setter
    private String pacients;
    @Column(name = "priority", nullable = false)
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private RecipePriority priority;

    public Recipe(String description, Date creation, Date exposure, String doctors, String pacients, RecipePriority priority) {
        this.description = description;
        this.creation = creation;
        this.exposure = exposure;
        this.pacients = pacients;
        this.doctors = doctors;
        this.priority = priority;
    }
}
