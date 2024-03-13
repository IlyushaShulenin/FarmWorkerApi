package ru.shulenin.farmworkerapi.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shulenin.farmworkerapi.datasource.entity.Product;

/**
 * Dto продукта для получения собщения
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReceiveDto {
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Product.Measure measure;
}
