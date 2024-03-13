package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shulenin.farmworkerapi.dto.ProductReceiveDto;
import ru.shulenin.farmworkerapi.service.ProductService;

/**
 * Вспомогательным контроллер для получения сообщений о продуктах со стороны владельца фермы
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/worker-api/v1/product")
@Slf4j
public class ProductRestController {
    private final ProductService productService;

    @PostMapping
    @KafkaListener(id = "ProductSave", topics = {"product.save"}, containerFactory = "singleFactory")
    public void save(ProductReceiveDto productDto) {
        log.info(String.format("ProductService.consume: message %s received", productDto));
        productService.save(productDto);
    }

    @DeleteMapping
    @KafkaListener(id = "ProductDelete", topics = {"product.delete"}, containerFactory = "singleFactory")
    public void delete(ProductReceiveDto productDto) {
        log.info(String.format("ProductService.delete: message %s received", productDto));
        productService.delete(productDto.getId());
    }
}
