package com.product.apex.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
}
