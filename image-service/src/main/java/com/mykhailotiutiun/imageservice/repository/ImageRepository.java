package com.mykhailotiutiun.imageservice.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageRepository {
    byte[] findByName(String name) throws IOException;

    String save(MultipartFile image) throws IOException;

    void delete(String name);
}
