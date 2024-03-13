package ru.shulenin.farmworkerapi.datasource.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность рабочего
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker implements Serializable {
    @Serial
    @Transient
    private static final long serialVersionUID = 3L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String surname;

//    @OneToMany(
//            cascade = CascadeType.REMOVE,
//            fetch = FetchType.LAZY,
//            mappedBy = "worker"
//    )
//    private List<Plan> plans = new ArrayList<>();
//
//    @OneToMany(
//            cascade = CascadeType.REMOVE,
//            fetch = FetchType.LAZY,
//            mappedBy = "worker"
//    )
//    private List<Score> scores = new ArrayList<>();
//
//    @OneToMany(
//            cascade = CascadeType.REMOVE,
//            fetch = FetchType.LAZY,
//            mappedBy = "worker"
//    )
//    private List<Report> reports = new ArrayList<>();

    public Worker(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

//    public void addPlan(Plan plan) {
//        plans.add(plan);
//        plan.setWorker(this);
//    }
//
//    public void addScore(Score score) {
//        scores.add(score);
//        score.setWorker(this);
//    }
//
//    public void addReport(Report report) {
//        reports.add(report);
//        report.setWorker(this);
//    }
}