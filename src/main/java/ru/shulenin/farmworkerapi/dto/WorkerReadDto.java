package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerReadDto extends AbstractDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
}
