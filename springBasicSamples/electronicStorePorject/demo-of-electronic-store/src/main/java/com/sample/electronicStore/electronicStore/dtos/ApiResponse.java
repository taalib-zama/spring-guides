package com.sample.electronicStore.electronicStore.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private String message;
    private boolean success;
    private HttpStatus status;

    public ResponseEntity<ApiResponse> toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }

    public static ResponseEntity<ApiResponse> of(String message, boolean success, HttpStatus status) {
        ApiResponse body = ApiResponse.builder()
                .message(message)
                .success(success)
                .status(status)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
