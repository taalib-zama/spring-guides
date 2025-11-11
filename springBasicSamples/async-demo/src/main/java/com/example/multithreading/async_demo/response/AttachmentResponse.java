package com.example.multithreading.async_demo.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponse {
    private Integer attachmentCount;
    private List<AttachmentStatus> attachmentStatuses;
}
