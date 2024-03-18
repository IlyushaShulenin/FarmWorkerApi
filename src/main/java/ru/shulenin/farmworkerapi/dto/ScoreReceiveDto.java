package ru.shulenin.farmworkerapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreReceiveDto {
    private Long id;
    private Long workerId;
    private Integer score;
    private LocalDate date;
}
