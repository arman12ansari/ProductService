package dev.arman.productservice.repositories;

import dev.arman.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Override
    List<Category> findAll();

    @Override
    Category save(Category category);
}
