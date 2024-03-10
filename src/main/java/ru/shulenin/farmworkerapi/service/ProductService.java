package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.dto.ProductReceiveDto;
import ru.shulenin.farmworkerapi.mapper.ProductMapper;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Transactional
    public void save(ProductReceiveDto productDto) {
        var product = productMapper.productReceiveDtoToProduct(productDto);
        productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
