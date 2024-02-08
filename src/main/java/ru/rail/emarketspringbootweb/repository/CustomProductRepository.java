package ru.rail.emarketspringbootweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.entity.Product;


import java.util.List;

public interface CustomProductRepository {
    Page<Product> findByProductDto(ProductDto productDto, Pageable pageable);
}
