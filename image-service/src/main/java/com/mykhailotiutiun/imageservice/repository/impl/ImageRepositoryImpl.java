package com.mykhailotiutiun.imageservice.repository.impl;

import com.mykhailotiutiun.imageservice.repository.ImageRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private String folderPath;

    @PostConstruct
    public void init(){
        folderPath = System.getProperty("user.dir") + "/image-service/src/main/resources/attachments";

        File folder = new File(folderPath);

        if(!folder.exists()){
            if(!folder.mkdirs()){
                throw new RuntimeException("Error creating dir: " + folderPath);
            }
        }
    }

    @Override
    public byte[] findByName(String name) throws IOException {
        return Files.readAllBytes(new File( folderPath + "/" + name).toPath());
    }

    @Override
    public String save(MultipartFile image) throws IOException {
        Random random = new Random();
        String name = random.nextInt() + 3_000_000_000L + ".jpg";
        image.transferTo(new File(folderPath + "/" + name));
        return name;
    }

    @Override
    public void delete(String name){
        File file = new File(folderPath + "/" + name);
        if(file.delete()){
            throw new RuntimeException("Error deleting: " + name);
        }
    }
}
