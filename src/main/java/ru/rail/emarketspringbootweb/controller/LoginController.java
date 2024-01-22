package ru.rail.emarketspringbootweb.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.service.UserService;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) throws Exception {
        Optional<UserDto> userOptional = userService.login(email, password);
        if (userOptional.isPresent()) {
            session.setAttribute("user", userOptional.get());
            return "redirect:/user";
        } else {
            model.addAttribute("error", true);
            return "login";
        }
    }
}
