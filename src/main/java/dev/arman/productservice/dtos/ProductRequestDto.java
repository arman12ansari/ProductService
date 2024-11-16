package dev.arman.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class ProductRequestDto {
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
}
