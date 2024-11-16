package dev.arman.productservice.services;

import dev.arman.productservice.exceptions.CategoryNotExistsException;
import dev.arman.productservice.exceptions.ProductNotExistsException;
import dev.arman.productservice.exceptions.UnableToAddProductException;
import dev.arman.productservice.models.Product;

import java.util.List;

/**
 * @author mdarmanansari
 */
public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotExistsException;

    List<Product> getAllProducts() throws ProductNotExistsException;

    List<Product> getProductsByCategory(String category) throws CategoryNotExistsException;

    List<String> getAllCategories() throws CategoryNotExistsException;

    Product addProduct(Product product) throws UnableToAddProductException;

    Product updateProduct(Long id, Product product) throws ProductNotExistsException;
}
