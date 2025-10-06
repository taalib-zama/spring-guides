package com.learning.core.JPADemo.spring_data_jpa_demo.service;


import ch.qos.logback.core.util.StringUtil;
import com.learning.core.JPADemo.spring_data_jpa_demo.entity.ErrorCodeMapping;
import com.learning.core.JPADemo.spring_data_jpa_demo.repository.ErrorCodeMappingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ErrorCodeMappingService {

    @Autowired
    private ErrorCodeMappingRepository errorCodeMappingRepository;

    public ErrorCodeMapping getOrCreate(String message) {
        if (StringUtils.length(message) > 255) {
            message = StringUtils.substring(message, 0, 255-4);
            message += "...";
        }

        Optional<ErrorCodeMapping> errrorCodeMappingEntity = errorCodeMappingRepository.findByMessage(message);
        if (errrorCodeMappingEntity.isPresent()) {
            return errrorCodeMappingEntity.get();
        } else {
            ErrorCodeMapping errorCodeMapping = ErrorCodeMapping.builder()
                    .withMessage(message)
                    .build();
            errorCodeMappingRepository.save(errorCodeMapping);
            return errorCodeMapping;
        }
    }
}
