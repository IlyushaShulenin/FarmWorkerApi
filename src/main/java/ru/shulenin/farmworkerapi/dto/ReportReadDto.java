package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportReadDto extends AbstractDto {
    private WorkerReadDto worker;
    private ProductReadDto product;
    private Float amount;
    private LocalDate date;
    private Boolean planIsCompleted;
}
