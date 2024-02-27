package ru.rail.emarketspringbootweb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.http.controller.ProductsListController;
import ru.rail.emarketspringbootweb.service.ProductService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProductsListController.class)
class ProductsListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Page<ProductDto> dummyPage;

    @BeforeEach
    void setUp() {
        ProductDto productDto = new ProductDto();
        dummyPage = new PageImpl<>(Collections.singletonList(productDto));
    }

    @Test
    void testShowAllProducts() throws Exception {
        Mockito.when(productService.getAllProducts(PageRequest.of(0, 3)))
                .thenReturn(dummyPage);

        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("productsPage"));
    }

    @Test
    void testShowFilteredProducts() throws Exception {
        Mockito.when(productService.getAllProducts(Mockito.any(ProductDto.class), Mockito.eq(0), Mockito.eq(3)))
                .thenReturn(dummyPage);

        mockMvc.perform(get("/products/filter")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("filterProducts"))
                .andExpect(model().attributeExists("productsPage"));
    }
}
