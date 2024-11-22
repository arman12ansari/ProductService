package dev.arman.productservice.services;

import dev.arman.productservice.exceptions.CategoryNotExistsException;
import dev.arman.productservice.exceptions.ProductNotExistsException;
import dev.arman.productservice.exceptions.UnableToAddProductException;
import dev.arman.productservice.models.Category;
import dev.arman.productservice.models.Product;
import dev.arman.productservice.repositories.CategoryRepository;
import dev.arman.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Service("selfProductService")
public class SelfProductService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }

        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotExistsException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotExistsException("No products found");
        }

        return products;
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws CategoryNotExistsException {
        List<Product> products = productRepository.findByCategory_Name(category);

        if (products.isEmpty()) {
            throw new CategoryNotExistsException("No products found for category " + category);
        }

        return products;
    }

    @Override
    public List<String> getAllCategories() throws CategoryNotExistsException {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new CategoryNotExistsException("No categories found");
        }

        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        return categoryNames;
    }

    @Override
    public Product addProduct(Product product) throws UnableToAddProductException {
        Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
        if (optionalCategory.isEmpty()) {
            product.setCategory(categoryRepository.save(product.getCategory()));
        } else {
            product.setCategory(optionalCategory.get());
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }

        Product existingProduct = optionalProduct.get();

        if (product.getTitle() != null) {
            existingProduct.setTitle(product.getTitle());
        }

        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }

        if (product.getPrice() != 0) {
            existingProduct.setPrice(product.getPrice());
        }

        if (product.getCategory() != null && product.getCategory().getName() != null) {
            Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
            if (optionalCategory.isEmpty()) {
                existingProduct.setCategory(categoryRepository.save(product.getCategory()));
            } else {
                existingProduct.setCategory(optionalCategory.get());
            }
        }

        if (product.getImageUrl() != null) {
            existingProduct.setImageUrl(product.getImageUrl());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }

        Product existingProduct = optionalProduct.get();
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null && product.getCategory().getName() != null) {
            Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
            if (optionalCategory.isEmpty()) {
                existingProduct.setCategory(categoryRepository.save(product.getCategory()));
            } else {
                existingProduct.setCategory(optionalCategory.get());
            }
        } else {
            existingProduct.setCategory(null);
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }

        Product product = optionalProduct.get();
        productRepository.deleteById(id);

        return product;
    }
}
