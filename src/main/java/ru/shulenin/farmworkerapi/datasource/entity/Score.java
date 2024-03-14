package ru.shulenin.farmworkerapi.datasource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Сущность баллов
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    private Integer score;

    private LocalDate date;

    public Score(Worker worker, Integer score, LocalDate date) {
        this.worker = worker;
        this.score = score;
        this.date = date;
    }
}

