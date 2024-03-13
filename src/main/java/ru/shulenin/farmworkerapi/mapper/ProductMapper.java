package ru.shulenin.farmworkerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.shulenin.farmworkerapi.datasource.entity.Product;
import ru.shulenin.farmworkerapi.dto.ProductReadDto;
import ru.shulenin.farmworkerapi.dto.ProductReceiveDto;

/**
 * Маппер для продуктов
 */
@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );


    /**
     * От сущности к dto для чтения
     * @param product сущность
     * @return dto для чтения
     */
    public ProductReadDto productToReadDto(Product product);

    /**
     * От сообщения к сущности
     * @param product сообщение
     * @return сущности
     */
    public Product productReceiveDtoToProduct(ProductReceiveDto product);
}
