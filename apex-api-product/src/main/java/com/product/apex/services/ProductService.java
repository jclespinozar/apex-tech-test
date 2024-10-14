package com.product.apex.services;

import com.product.apex.entities.Product;
import com.product.apex.entities.ProductDTO;
import com.product.apex.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));

        return convertToDTO(product);
    }

    public List<ProductDTO> getProductsByIds(List<Long> productIds) {
        List<Product> products = productRepository.findByIdIn(productIds);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setNombre(product.getNombre());
        dto.setDescripcion(product.getDescripcion());
        dto.setPrecio(product.getPrecio());
        return dto;
    }
}
