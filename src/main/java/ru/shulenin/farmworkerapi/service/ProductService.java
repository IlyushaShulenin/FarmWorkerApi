package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.dto.ProductReceiveDto;
import ru.shulenin.farmworkerapi.mapper.ProductMapper;

/**
 * Сервис для работы с продуктоами
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    /**
     * сохранение продукта
     * @param productDto dto для сообщения
     */
    @Transactional
    public void save(ProductReceiveDto productDto) {
        var product = productMapper.productReceiveDtoToProduct(productDto);
        productRepository.save(product);

        log.info(String.format("ProductService.save(): entity %s saved", product));
    }

    /**
     * удаление продукта
     * @param id id проудкта
     */
    @Transactional
    public void delete(Long id) {
        productRepository.cancelProduct(id);
        log.info(String.format("WorkerService.delete(): entity with id= %d deleted", id));
    }
}
