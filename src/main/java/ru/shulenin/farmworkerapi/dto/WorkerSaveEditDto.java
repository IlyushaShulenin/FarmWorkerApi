package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto рабочего для сохранения
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerSaveEditDto extends AbstractDto {
    private String email;
    private String name;
    private String surname;
}
