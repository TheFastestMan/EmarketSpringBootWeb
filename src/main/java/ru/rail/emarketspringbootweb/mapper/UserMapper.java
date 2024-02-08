package ru.rail.emarketspringbootweb.mapper;




import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.entity.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto userDTO);

    UserDto toDTO(User user);
}
