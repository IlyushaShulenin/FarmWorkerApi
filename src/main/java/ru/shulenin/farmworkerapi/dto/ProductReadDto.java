package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shulenin.farmworkerapi.datasource.entity.Product;


/**
 * Dto продукта для чтения
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReadDto extends AbstractDto {
    private Long id;
    private String name;
    private Product.Measure measure;
}
