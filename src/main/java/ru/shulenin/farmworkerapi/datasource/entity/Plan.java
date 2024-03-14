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
 * Сущность плана работ
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Float amount;

    private LocalDate date;

    public Plan(Worker worker, Product product, Float amount, LocalDate date) {
        this.worker = worker;
        this.product = product;
        this.amount = amount;
        this.date = date;
    }
}
