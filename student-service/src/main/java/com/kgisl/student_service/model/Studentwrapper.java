package com.kgisl.student_service.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Studentwrapper {
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate birthdate;
    private String address;
    private String phone_number;
    private String department; 

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate dateOfJoining;
    
    
}
