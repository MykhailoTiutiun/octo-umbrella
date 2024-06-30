package com.mykhailotiutiun.imageservice.controller;

import com.mykhailotiutiun.imageservice.repository.ImageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/images")
public class ImageController {

    private final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageRepository.findByName(name));
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody MultipartFile image) throws IOException {
        return new ResponseEntity<>(imageRepository.save(image), HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
        imageRepository.delete(name);
    }
}
