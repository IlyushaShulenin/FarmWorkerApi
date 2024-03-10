package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shulenin.farmworkerapi.dto.ProductReceiveDto;
import ru.shulenin.farmworkerapi.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/worker-api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @KafkaListener(id = "ProductSave", topics = {"product.save"}, containerFactory = "singleFactory")
    public void save(ProductReceiveDto productDto) {
        productService.save(productDto);
    }

    @DeleteMapping
    @KafkaListener(id = "ProductDelete", topics = {"product.delete"}, containerFactory = "singleFactory")
    public void delete(ProductReceiveDto productDto) {
        productService.delete(productDto.getId());
    }
}
