package ru.rail.emarketspringbootweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.service.CartService;
import ru.rail.emarketspringbootweb.service.ProductService;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
@SessionAttributes("user")
public class CartController {

    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long productId,
                                       @RequestParam int quantity,
                                       @SessionAttribute("user") UserDto userDto) {
        try {
            Optional<ProductDto> productDtoOptional = productService.getProductById(productId);
            if (productDtoOptional.isPresent() && productDtoOptional.get().getQuantity() >= quantity) {
                ProductDto productDto = productDtoOptional.get();
                cartService.addProductToCart(userDto, productDto, quantity);
                return ResponseEntity.ok().body("Product added to cart successfully");
            } else {
                return ResponseEntity.badRequest().body("Product cannot be added to cart");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to cart");
        }
    }
}