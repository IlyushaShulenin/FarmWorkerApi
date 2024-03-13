package ru.shulenin.farmworkerapi.datasource.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Сущность отчета
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {
    @Serial
    @Transient
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Float amount;

    private LocalDate date;

    private Boolean planIsCompleted;

    public Report(Worker worker, Product product, Float amount, LocalDate date) {
        this.worker = worker;
        this.product = product;
        this.amount = amount;
        this.date = date;
    }
}
