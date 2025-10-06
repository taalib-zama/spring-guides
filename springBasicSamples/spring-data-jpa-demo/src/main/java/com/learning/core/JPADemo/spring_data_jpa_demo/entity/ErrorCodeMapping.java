package com.learning.core.JPADemo.spring_data_jpa_demo.entity;

import com.learning.core.JPADemo.spring_data_jpa_demo.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder", setterPrefix = "with")
@Table(name = "ERROR_CODE_MAPPING", schema = "sample")
public class ErrorCodeMapping  extends AbstractEntity {

    @Column
    private String message;

}
