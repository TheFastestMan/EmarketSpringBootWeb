package ru.rail.emarketspringbootweb.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import ru.rail.emarketspringbootweb.validation.LoginValidation;

@LoginValidation
@Getter
@Setter
public class LoginDTO {

    private String email;
    private String password;
}
