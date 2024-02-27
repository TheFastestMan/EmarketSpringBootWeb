package ru.rail.emarketspringbootweb.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.entity.Gender;
import ru.rail.emarketspringbootweb.entity.Role;
import ru.rail.emarketspringbootweb.service.UserService;


@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegistration(Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("userDTO", new UserDto());
        return "registration";
    }

    @PostMapping// Решить как перевести на REST
    public String handleRegistration(
            @ModelAttribute("userDTO") @Validated UserDto userDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            model.addAttribute("genders", Gender.values());
            return "registration";
        }
        userService.create(userDto);
        return "login";
    }

}
