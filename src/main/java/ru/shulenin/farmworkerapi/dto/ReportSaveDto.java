package ru.shulenin.farmworkerapi.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Dto отчета для сохранения
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportSaveDto extends AbstractDto {
    @Positive(message = "workerId must be positive")
    private Long workerId;

    @Positive(message = "productId must positive")
    private Long productId;

    @Positive(message = "amount must be positive")
    private Float amount;

    @PastOrPresent(message ="date can not be a future")
    private LocalDate date;
}
