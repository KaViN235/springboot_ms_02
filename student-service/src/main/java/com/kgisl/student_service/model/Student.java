package com.kgisl.student_service.model;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentId;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthdate;
    private String address;
    private String phone_number;
    private String email;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfJoining;

    @Column(name = "year_of_study")
    private Integer yearOfStudy;
    private Boolean isYearCompleted = false;
    private String department;

    @ElementCollection
    private List<Integer> courseIds;

}
