package ru.rail.emarketspringbootweb.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductsListController {
    @Autowired
    private final ProductService productService;

    @Autowired
    public ProductsListController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String showAllProducts(@ModelAttribute("productDTO") ProductDto productDto,
                                       Model model,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> productsPage = productService.getAllProducts(pageable);
        model.addAttribute("productsPage", productsPage);
        return "products";
    }


    @GetMapping("/filter")
    public String showFilteredProducts(@ModelAttribute("productDTO") ProductDto productDto,
                                       Model model,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "3") int size) {
        Page<ProductDto> productsPage = productService.getAllProducts(productDto, page, size);
        model.addAttribute("productsPage", productsPage);
        return "filterProducts";
    }


}

