package ru.rail.emarketspringbootweb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.entity.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "image", target = "image", qualifiedByName = "multipartFileToString")
    User toEntity(UserDto userDTO);

    @Mapping(target = "image", ignore = true) // Предполагается, что при преобразовании в DTO вам не нужно возвращать MultipartFile
    UserDto toDTO(User user);

    @Named("multipartFileToString")
    default String multipartFileToString(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            // Здесь должна быть логика сохранения файла и возвращение строки пути к файлу
            // Например, сохранение файла и возвращение его имени или пути
            // Это псевдокод, необходимо реализовать логику сохранения файла
            return "" + file.getOriginalFilename();
        }
        return null; // или какое-то стандартное значение, если файл отсутствует
    }
}
