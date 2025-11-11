package com.learning.core.JPADemo.spring_data_jpa_demo.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder (builderClassName = "Builder", setterPrefix = "with")
@Table(name = "USER", schema = "sample")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Integer id;

    @Column(name = "USER_NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "USER_CITY", length = 50)
    private String city;

    @Column(name = "USER_AGE")
    private Integer age;
}
