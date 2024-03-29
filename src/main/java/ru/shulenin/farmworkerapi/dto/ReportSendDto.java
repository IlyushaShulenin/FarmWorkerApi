package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Dto отчета для отправки сообщения
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportSendDto {
    private Long id;
    private Long workerId;
    private Long productId;
    private Float amount;
    private LocalDate date;
    private Boolean planIsCompleted;
}
