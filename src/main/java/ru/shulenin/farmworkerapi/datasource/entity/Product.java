package ru.shulenin.farmworkerapi.datasource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность продукта
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Measure measure;

    public enum Measure {
        LITER, KG, UNIT
    }

    public Product(String name, Measure measure) {
        this.name = name;
        this.measure = measure;
    }
}
