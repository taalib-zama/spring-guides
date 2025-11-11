package com.sample.electronicStore.electronicStore.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "sample", name = "entity_sequence", sequenceName = "entity_sequence", allocationSize = 100, initialValue = 1)
    public Long id;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy;

    @Column (name  = "CREATED_DATE")
    @CreatedDate
    private Date createdDate;


    @Column(name = "UPDATED_BY")
    @LastModifiedBy
    private String updatedBy;

    @Column (name  = "UPDATED_DATE")
    @LastModifiedDate
    private Date updatedDate;

    @Column(nullable = false)
    private boolean active = true;

}
