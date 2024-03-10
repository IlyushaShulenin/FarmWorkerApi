package ru.shulenin.farmworkerapi.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportSaveDto extends AbstractDto {
    @Positive
    private Long workerId;

    @Positive
    private Long productId;

    @Positive
    private Float amount;

    @PastOrPresent
    private LocalDate date;
}
