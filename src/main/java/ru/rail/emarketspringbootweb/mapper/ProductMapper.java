package ru.rail.emarketspringbootweb.mapper;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.entity.Product;

@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto productDto);

    ProductDto toDTO(Product product);
}
