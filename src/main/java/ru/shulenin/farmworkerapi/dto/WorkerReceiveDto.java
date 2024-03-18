package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto рабочего для получения сообщения
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerReceiveDto extends AbstractDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;

}
