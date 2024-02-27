package ru.rail.emarketspringbootweb.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rail.emarketspringbootweb.service.ImageService;
import ru.rail.emarketspringbootweb.service.UserService;

import static org.springframework.http.ResponseEntity.notFound;


@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageRestController {
    @Autowired
    private final UserService userService;

    @GetMapping(value = "/{id}") // Пример для JPEG
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }
}
