package ru.rail.emarketspringbootweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return "registration";
    }

    @PostMapping
    public String handleRegistration(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam Role role,
            @RequestParam Gender gender,
            Model model) {
        UserDto userDto = UserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .gender(gender)
                .build();

        try {
            userService.create(userDto);
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "There was a problem creating your account. Please try again.");
            return getRegistration(model);
        }
    }
}
