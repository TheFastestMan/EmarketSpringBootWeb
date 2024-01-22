package ru.rail.emarketspringbootweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.rail.emarketspringbootweb.dto.UserDto;

@Controller
public class UserController {

    @GetMapping("/user")
    public String getUserPage(
            @SessionAttribute(name = "user", required = false) UserDto user, // Gets the 'user' attribute from the session
            Model model) {

        if (user == null) {
            model.addAttribute("error", "Please login to view user details.");
            return "errorPage";
        }

        model.addAttribute("user", user);
        return "user";
    }
}
