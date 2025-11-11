package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.entities.ErrorCodeMapping;
import com.sample.electronicStore.electronicStore.repo.ErrorCodeMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditWriter {
    @Autowired
    private ErrorCodeMappingRepository errorCodeMappingRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ErrorCodeMapping save(ErrorCodeMapping mapping) {
        return errorCodeMappingRepository.save(mapping);
    }
}
