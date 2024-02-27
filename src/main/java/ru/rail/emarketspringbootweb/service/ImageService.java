package ru.rail.emarketspringbootweb.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final UserService userService;

    @Value("${bucket}")
    private String bucket;


    @SneakyThrows
    public Optional<byte[]> getImage(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try {
            return Files.exists(fullImagePath)
                    ? Optional.of(Files.readAllBytes(fullImagePath))
                    : Optional.empty();
        } catch (IOException e) {
            System.err.println("Error reading image file: " + e.getMessage());
            return Optional.empty();
        }
    }

    @SneakyThrows
    public void upload(String filename, InputStream content) { // загружаем изображение
        Path filePath = Path.of(bucket, filename);

        try(content) {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, content.readAllBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

}
