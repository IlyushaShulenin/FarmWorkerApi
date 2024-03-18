package ru.shulenin.farmworkerapi.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shulenin.farmworkerapi.datasource.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query(
            value = "UPDATE product SET is_produced = false WHERE id = :id",
            nativeQuery = true
    )
    public void cancelProduct(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM product WHERE is_produced = true",
            nativeQuery = true
    )
    public List<Product> findAll();

    @Query(
            value = "SELECT * FROM product WHERE is_produced = true AND id = :id",
            nativeQuery = true
    )
    public Optional<Product> findById(@Param("id") Long id);
}
