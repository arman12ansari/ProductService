package dev.arman.productservice.services;

import dev.arman.productservice.dtos.FakeStoreProductDto;
import dev.arman.productservice.dtos.ProductRequestDto;
import dev.arman.productservice.exceptions.CategoryNotExistsException;
import dev.arman.productservice.exceptions.ProductNotExistsException;
import dev.arman.productservice.exceptions.UnableToAddProductException;
import dev.arman.productservice.models.Category;
import dev.arman.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Service
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException {
        FakeStoreProductDto fakeStoreProductDto =
                restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);

        if (fakeStoreProductDto == null) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }

        return convertFakeStoreProductToProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotExistsException {
        FakeStoreProductDto[] response =
                restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);

        if (response == null || response.length == 0) {
            throw new ProductNotExistsException("No products found");
        }

        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDto fakeStoreProductDto : response) {
            products.add(convertFakeStoreProductToProduct(fakeStoreProductDto));
        }

        return products;
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws CategoryNotExistsException {
        FakeStoreProductDto[] response =
                restTemplate.getForObject("https://fakestoreapi.com/products/category/" + category, FakeStoreProductDto[].class);

        if (response == null || response.length == 0) {
            throw new CategoryNotExistsException("Category " + category + " does not exist");
        }

        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDto fakeStoreProductDto : response) {
            products.add(convertFakeStoreProductToProduct(fakeStoreProductDto));
        }

        return products;
    }

    @Override
    public List<String> getAllCategories() throws CategoryNotExistsException {
        String[] response =
                restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);

        if (response == null || response.length == 0) {
            throw new CategoryNotExistsException("No categories found");
        }

        return List.of(response);
    }

    @Override
    public Product addProduct(Product product) throws UnableToAddProductException {
        ProductRequestDto request = convertProductToProductRequestDto(product);

        FakeStoreProductDto response =
                restTemplate.postForObject("https://fakestoreapi.com/products", request, FakeStoreProductDto.class);

        if (response == null) {
            throw new UnableToAddProductException("Unable to add product");
        }

        return convertFakeStoreProductToProduct(response);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotExistsException {
        ProductRequestDto request = convertProductToProductRequestDto(product);

        FakeStoreProductDto response =
                restTemplate.patchForObject("https://fakestoreapi.com/products/" + id, request, FakeStoreProductDto.class);

        if (response == null) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }

        return convertFakeStoreProductToProduct(response);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotExistsException {
        ProductRequestDto request = convertProductToProductRequestDto(product);

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response =
                restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor);

        if (response == null) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }

        return convertFakeStoreProductToProduct(response);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotExistsException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(null, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response =
                restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.DELETE, requestCallback, responseExtractor);

        if (response == null) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }

        return convertFakeStoreProductToProduct(response);
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();

        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProductDto.getCategory());

        return product;
    }

    private ProductRequestDto convertProductToProductRequestDto(Product product) {
        ProductRequestDto productRequestDto = new ProductRequestDto();

        if (product.getTitle() != null) {
            productRequestDto.setTitle(product.getTitle());
        }

        if (product.getPrice() != 0) {
            productRequestDto.setPrice(product.getPrice());
        }

        if (product.getDescription() != null) {
            productRequestDto.setDescription(product.getDescription());
        }

        if (product.getImageUrl() != null) {
            productRequestDto.setImage(product.getImageUrl());
        }

        if (product.getCategory() != null && product.getCategory().getName() != null) {
            productRequestDto.setCategory(product.getCategory().getName());
        }

        return productRequestDto;
    }
}
