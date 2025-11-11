package com.sample.electronicStore.electronicStore.services;


import com.sample.electronicStore.electronicStore.entities.ErrorCodeMapping;
import com.sample.electronicStore.electronicStore.repo.ErrorCodeMappingRepository;
import com.sample.electronicStore.electronicStore.services.impl.AuditWriter;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ErrorCodeMappingService {

    @Autowired
    private AuditWriter auditWriter;

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
            //errorCodeMappingRepository.save(errorCodeMapping);

            // when creating new mapping, persist via auditWriter.save(...
            auditWriter.save(errorCodeMapping);
            return errorCodeMapping;
        }
    }
}
