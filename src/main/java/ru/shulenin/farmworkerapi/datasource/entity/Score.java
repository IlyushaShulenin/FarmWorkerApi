package ru.shulenin.farmworkerapi.datasource.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Сущность баллов
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score implements Serializable {
    @Serial
    @Transient
    private static final long serialVersionUID = 1L;

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

