package ru.rail.emarketspringbootweb.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.service.ProductService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private final ProductService productService;

    public UserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/user")
    public String getUserPage(@SessionAttribute(name = "user", required = false) UserDto user,
                              Model model) {

        List<ProductDto> userItems = productService.getProductsByUserId(user.getId());
        if (userItems.size() > 3) {
            userItems = userItems.subList(0, 3); // Keep only the first three items
        }

        model.addAttribute("cartTotal", productService.calculateTotalCartValue(user.getId()));
        model.addAttribute("userItems", userItems);
        model.addAttribute("user", user);

        if (user == null) {
            model.addAttribute("error", "Please login to view user details.");
            return "errorPage";
        }

        model.addAttribute("user", user);
        return "user";
    }


    @GetMapping("/userItems")
    public String getUserCartItems(@SessionAttribute(name = "user", required = false) UserDto user,
                                   Model model) {
        if (user == null) {
            model.addAttribute("error", "Please login to view your cart items.");
            return "errorPage";
        }

        model.addAttribute("cartTotal", productService.calculateTotalCartValue(user.getId()));
        model.addAttribute("userItems", productService.getProductsByUserId(user.getId()));

        return "userItems";
    }
}
