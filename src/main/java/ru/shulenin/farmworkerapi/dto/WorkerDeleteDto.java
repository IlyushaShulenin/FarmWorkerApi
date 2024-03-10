package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDeleteDto extends AbstractDto {
    private Long id;
}
