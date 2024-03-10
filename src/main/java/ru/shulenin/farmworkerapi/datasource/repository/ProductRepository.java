package ru.shulenin.farmworkerapi.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shulenin.farmworkerapi.datasource.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
