package ru.rail.emarketspringbootweb.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.service.ProductService;


@Controller
public class ProductController {
    @Autowired
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/addProducts")
    public String showAddProductsForm(@ModelAttribute("productDTO") ProductDto productDto,
                                      Model model) {
        model.addAttribute("productDTO", productDto);
        return "addProducts";
    }



    @PostMapping("/addProducts") // Решить как перевести на REST
    public String addProduct(@ModelAttribute("productDTO") @Validated ProductDto productDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "addProducts";
        }

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
