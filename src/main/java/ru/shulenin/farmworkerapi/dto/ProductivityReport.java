package ru.shulenin.farmworkerapi.dto;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto выработки рабочего
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductivityReport {
    private WorkerReadDto worker;
    private ProductReadDto product;
    private Double reportAmount;
    private Double planAmount;
}
