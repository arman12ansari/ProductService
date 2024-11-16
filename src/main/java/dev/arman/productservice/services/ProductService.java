package dev.arman.productservice.services;

import dev.arman.productservice.exceptions.ProductNotExistsException;
import dev.arman.productservice.models.Product;

/**
 * @author mdarmanansari
 */
public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotExistsException;
}
