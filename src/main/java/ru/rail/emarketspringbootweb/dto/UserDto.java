package ru.rail.emarketspringbootweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import ru.rail.emarketspringbootweb.entity.Gender;
import ru.rail.emarketspringbootweb.entity.Role;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, message = "Name should not be less than 5")
    private String username;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Role should not be empty")
    private Role role;

    @NotNull(message = "Gender should not be empty")
    private Gender gender;

    @Size(min = 5, message = "Password should be not less than 5 characters")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    private  MultipartFile image;

}
