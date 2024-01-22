package ru.rail.emarketspringbootweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.service.ProductService;

import java.util.List;


@Controller
public class ProductController {
@Autowired
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/addProducts")
    public String showAddProductsForm() {
        return "addProducts";
    }

    @GetMapping("/allProducts")
    public String showAllProducts(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> productsPage = productService.getAllProducts(pageable);
        model.addAttribute("productsPage", productsPage);
        return "allProducts";
    }


    @PostMapping("/addProducts")
    public String addProduct(
            @RequestParam String productName,
            @RequestParam String description,
            @RequestParam Integer quantity,
            @RequestParam Double price,
            RedirectAttributes redirectAttributes) {

        ProductDto productDto = ProductDto.builder()
                .productName(productName)
                .description(description)
                .quantity(quantity)
                .price(price)
                .build();

        try {
            productService.addProduct(productDto);

            redirectAttributes.addFlashAttribute("successMessage", "Product added successfully");
            return "redirect:/user";
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("errorMessage", "Error adding the product");
            return "redirect:/addProducts";
        }
    }

}
