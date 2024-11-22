package dev.arman.productservice.repositories;

import dev.arman.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Product save(Product product);

    @Override
    Optional<Product> findById(Long id);

    @Override
    List<Product> findAll();

    List<Product> findByCategory_Name(String name);

    @Override
    void deleteById(Long id);
}
