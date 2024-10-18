package com.kgisl.student_service.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Course {
    
    private Long id;
    private String name;
    private String type;
    @Column(name = "year_of_study")
    private Integer yearOfStudy;
    private String department;

}

