package ru.rail.emarketspringbootweb.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rail.emarketspringbootweb.dto.LoginDTO;
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
    public String getLoginPage(@ModelAttribute("userDTO") LoginDTO error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("userDTO") @Validated LoginDTO userDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) throws Exception {
        if (bindingResult.hasErrors()) {
            // Global errors can be added to the model or as flash attributes!!!!!!!!!!!!!
            return "login"; // Return here if there are validation errors
        }

        Optional<UserDto> userOptional = userService.login(userDto.getEmail(), userDto.getPassword());
        if (userOptional.isPresent()) {
            session.setAttribute("user", userOptional.get());
            return "redirect:/user";
        } else {
            // Handle login failure (this block might not be necessary if the validator is working properly)
            bindingResult.reject("login.invalid", "Email or password is not correct");
            return "login";
        }
    }

}
