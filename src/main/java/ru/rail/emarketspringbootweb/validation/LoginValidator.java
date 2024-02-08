package ru.rail.emarketspringbootweb.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rail.emarketspringbootweb.dto.LoginDTO;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.entity.User;
import ru.rail.emarketspringbootweb.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class LoginValidator implements ConstraintValidator<LoginValidation, LoginDTO> {
    @Autowired
    private final UserRepository userRepository;

    public LoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(LoginDTO value, ConstraintValidatorContext context) {

        Optional<User> user = userRepository.findByEmailAndPassword(value.getEmail(), value.getPassword());

        if (!user.isPresent()) {
            return false;
        } else
            return true;
    }
}
