package ru.rail.emarketspringbootweb.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.http.handler.ApiException;
import ru.rail.emarketspringbootweb.service.CartService;
import ru.rail.emarketspringbootweb.service.ProductService;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@SessionAttributes("user")
@RequiredArgsConstructor
public class CartRestController {
    private final ProductService productService;
    private final CartService cartService;


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
                throw new ApiException(HttpStatus.BAD_REQUEST, "Product cannot be added to cart");
            }
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding product to cart");
        }
    }

}
