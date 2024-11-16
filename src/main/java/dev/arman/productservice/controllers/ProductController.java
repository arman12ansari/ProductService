package dev.arman.productservice.controllers;

import dev.arman.productservice.exceptions.CategoryNotExistsException;
import dev.arman.productservice.exceptions.ProductNotExistsException;
import dev.arman.productservice.models.Product;
import dev.arman.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mdarmanansari
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) throws ProductNotExistsException {
        return new ResponseEntity<>(productService.getSingleProduct(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() throws ProductNotExistsException {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("categoryName") String categoryName) throws CategoryNotExistsException {
        return new ResponseEntity<>(productService.getProductsByCategory(categoryName), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() throws CategoryNotExistsException {
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }
}
