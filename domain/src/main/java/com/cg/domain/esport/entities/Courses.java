package com.cg.domain.esport.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

public class Courses extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String name;

    @Column(columnDefinition = "Text")
    private String description;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Column(name="type_id", nullable = false)
    private int typeId;

    @Column(name = "mentor_id", nullable = false)
    private long mentorId;

    @Column(columnDefinition = "decimal(12,0)")
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "default true")
    private boolean free;
}
