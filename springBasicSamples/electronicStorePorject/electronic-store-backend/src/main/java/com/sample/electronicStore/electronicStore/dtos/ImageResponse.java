package com.sample.electronicStore.electronicStore.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@Builder
public class ImageResponse {
    private String imageName;
        private String message;
        private boolean success;
        private HttpStatus status;


}
