package dev.arman.productservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
@Entity
public class Category extends BaseModel {
    private String name;
}
