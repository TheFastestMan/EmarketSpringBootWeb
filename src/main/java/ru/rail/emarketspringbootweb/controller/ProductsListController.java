package ru.rail.emarketspringbootweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.service.ProductService;


import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsListController {
    @Autowired
    private final ProductService productService;

    @Autowired
    public ProductsListController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping
//    public String listProducts(Model model) {
//        try {
//            List<ProductDto> products = productService.getAllProducts();
//            model.addAttribute("pr", products);
//            return "products";
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            model.addAttribute("errorMessage", "Error processing the request");
//            return "error";
//        }

        @GetMapping
        public String listProducts(Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size) {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDto> productsPage = productService.getAllProducts(pageable);
            model.addAttribute("productsPage", productsPage);
            return "products";
        }

}

