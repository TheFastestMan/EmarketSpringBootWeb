package ru.rail.emarketspringbootweb.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.entity.User;
import ru.rail.emarketspringbootweb.mapper.UserMapper;
import ru.rail.emarketspringbootweb.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;

    private final ImageService imageService;

    public UserService(UserRepository userRepository, UserMapper userMapper,@Lazy ImageService imageService) {
        this.userRepository = userRepository;

        this.userMapper = userMapper;
        this.imageService = imageService;
    }

    @Transactional
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


    public List<UserDto> findAllUser() {

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

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }


    @Transactional
    public User create(UserDto userDto) {
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userMapper.toEntity(dto);
                })
                .map(userRepository::save)
                .orElseThrow();
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

}


