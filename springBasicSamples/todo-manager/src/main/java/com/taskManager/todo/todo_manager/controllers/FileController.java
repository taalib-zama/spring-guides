package com.taskManager.todo.todo_manager.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/file")

public class FileController {
    Logger log = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/single")
    public String uploadSingle(@RequestParam ("image") MultipartFile file) {
        log.info("file name : {} ", file.getOriginalFilename());
        log.info("file size : {} ", file.getSize());
        log.info("file content type : {} ", file.getContentType());

        /*//reading file content
        try {
            String content = new String(file.getBytes());
            log.info("file content : {} ", content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        /*
        //logic to save the file
        try {
            file.transferTo(new File("C:\\Users\\dell\\OneDrive\\Desktop\\
" + file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //return ResponseEntity.status(HttpStatus.OK).body("file uploaded successfully");
        //upload file to azure blob storage
        String connectStr = "DefaultEndpointsProtocol=https;AccountName=your_account_name;Account
Key=your_account_key;EndpointSuffix=core.windows.net";
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).
buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("your_container_name");
        BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());
        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "file uploaded to azure blob storage successfully";



         */
        return "file upload test";
    }

    //handle multiple file upload
    @PostMapping("/multiple")
    public String uploadMultiple(@RequestParam ("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            log.info("file name : {} ", file.getOriginalFilename());
            log.info("file size : {} ", file.getSize());
            log.info("file content type : {} ", file.getContentType());
        }
        return "file upload test";
    }

    //sending file as response
    @GetMapping("/serve-image")
    public void serveImage(HttpServletResponse response) {
        //logic to read file from disk or database
        try {
            //read file from local disk
            FileInputStream fileInputStream = new  FileInputStream("/Users/taalibzama/Desktop/sample.png");
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //return file as response entity with appropriate content type
    }
}
