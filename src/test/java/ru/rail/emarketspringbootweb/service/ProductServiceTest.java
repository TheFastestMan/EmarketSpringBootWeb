package ru.rail.emarketspringbootweb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.entity.Product;
import ru.rail.emarketspringbootweb.mapper.ProductMapper;
import ru.rail.emarketspringbootweb.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Test Product", "Description", 10.0, 5, null);
        productDto = new ProductDto(1L, "Test Product", "Description", 5, 10.0);

        when(productMapper.toDTO(any(Product.class))).thenReturn(productDto);
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
    }

    @Test
    void getAllProducts() throws Exception {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductDto> products = productService.getAllProducts();

        assertEquals(1, products.size());
        ProductDto actualProductDto = products.get(0);
        assertProductDtoEquals(productDto, actualProductDto);
    }

    private void assertProductDtoEquals(ProductDto expected, ProductDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getProductName(), actual.getProductName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getQuantity(), actual.getQuantity());
        assertEquals(expected.getPrice(), actual.getPrice());
    }


    @Test
    void getProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<ProductDto> foundProduct = productService.getProductById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals(productDto, foundProduct.get());
    }

    @Test
    void getAllProductsPageable() {
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        PageRequest pageRequest = PageRequest.of(0, 10);

        when(productRepository.findAll(pageRequest)).thenReturn(productPage);

        Page<ProductDto> products = productService.getAllProducts(pageRequest);

        assertEquals(1, products.getContent().size());
        assertEquals(productDto, products.getContent().get(0));
    }
}
