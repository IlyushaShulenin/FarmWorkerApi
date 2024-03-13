package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Dto баллов для чтения
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreReadDto {
    private WorkerReadDto workerReadDto;
    private Integer score;
    private LocalDate date;
}
