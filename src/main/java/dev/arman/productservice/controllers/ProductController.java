package dev.arman.productservice.controllers;

import dev.arman.productservice.exceptions.CategoryNotExistsException;
import dev.arman.productservice.exceptions.ProductNotExistsException;
import dev.arman.productservice.exceptions.UnableToAddProductException;
import dev.arman.productservice.models.Product;
import dev.arman.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws UnableToAddProductException {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotExistsException {
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotExistsException {
        return new ResponseEntity<>(productService.replaceProduct(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) throws ProductNotExistsException {
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }
}
