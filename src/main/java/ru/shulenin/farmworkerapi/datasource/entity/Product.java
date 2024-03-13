package ru.shulenin.farmworkerapi.datasource.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Сущность продукта
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Serial
    @Transient
    private static final long serialVersionUID = 3L;

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
