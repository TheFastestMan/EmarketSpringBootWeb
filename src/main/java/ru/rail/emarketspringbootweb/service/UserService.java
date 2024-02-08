package ru.rail.emarketspringbootweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.entity.User;
import ru.rail.emarketspringbootweb.mapper.UserMapper;
import ru.rail.emarketspringbootweb.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;

        this.userMapper = userMapper;
    }


    public Optional<UserDto> login(String email, String password) throws Exception {

        return userRepository.findByEmailAndPassword(email, password)
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    dto.setGender(user.getGender());
                    dto.setPassword(user.getPassword());
                    return dto;
                });
    }


    public List<UserDto> findAllUser() throws Exception {

        return userRepository.findAll().stream()
                .map(user -> {
                            UserDto dto = new UserDto();
                            dto.setId(user.getId());
                            dto.setUsername(user.getUsername());
                            dto.setEmail(user.getEmail());
                            dto.setRole(user.getRole());
                            dto.setGender(user.getGender());
                            dto.setPassword(user.getPassword());
                            return dto;
                        }
                ).collect(Collectors.toList());
    }

    public User create(UserDto userDto) {

//        var validationFactory = Validation.buildDefaultValidatorFactory();
//        var validator = validationFactory.getValidator();
//        var validationResult = validator.validate(userDto);
//
//        if (!validationResult.isEmpty()) {
//            throw new ConstraintViolationException(validationResult);
//        }
        User user = userMapper.toEntity(userDto);

        var result = userRepository.save(user);
        return result;
    }

}