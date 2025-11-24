package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.exceptions.BadApiRequestException;
import com.sample.electronicStore.electronicStore.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        log.info("File name : {}",originalFilename);
        //generating auto file name.
        String fileName = UUID.randomUUID().toString();
        //apending file extension
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileWithExtension = fileName + extension;
        log.info("File with extension : {}",fileWithExtension);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            log.info("File is valid");
            //save file to folder and db
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            //create full path
            String fullPath = path + File.separator + fileWithExtension;

            // Copy file
            Files.copy(file.getInputStream(),
                    new File(fullPath).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return fileWithExtension;
        } else {
            log.info("File is not valid");
            throw new BadApiRequestException("File with this " + extension + " is not allowed");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new java.io.FileInputStream(fullPath);
        return inputStream;
    }
}
