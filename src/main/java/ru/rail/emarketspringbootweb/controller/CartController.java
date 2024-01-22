package ru.rail.emarketspringbootweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.service.CartService;
import ru.rail.emarketspringbootweb.service.ProductService;

import java.util.Optional;

@Controller
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
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            @SessionAttribute("user") UserDto userDto, // This will get the user from session attributes
                            RedirectAttributes redirectAttributes) {
        System.out.println("addToCart method called with productId: " + productId + ", userId: " + userDto.getId() + ", quantity: " + quantity);

        try {
            Optional<ProductDto> productDtoOptional = productService.getProductById(productId);

            if (productDtoOptional.isPresent() && productDtoOptional.get().getQuantity() >= quantity) {
                ProductDto productDto = productDtoOptional.get();

                cartService.addProductToCart(userDto, productDto, quantity);

                redirectAttributes.addFlashAttribute("addToCartSuccess", true);
            } else {
                redirectAttributes.addFlashAttribute("addToCartError", true);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("addToCartError", true);
        }
        return "redirect:/products";
    }
}
