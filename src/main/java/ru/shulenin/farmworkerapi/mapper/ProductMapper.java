package ru.shulenin.farmworkerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.shulenin.farmworkerapi.datasource.entity.Product;
import ru.shulenin.farmworkerapi.dto.ProductReadDto;
import ru.shulenin.farmworkerapi.dto.ProductReceiveDto;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    public ProductReadDto productToReadDto(Product product);
    public Product productReceiveDtoToProduct(ProductReceiveDto product);
}
