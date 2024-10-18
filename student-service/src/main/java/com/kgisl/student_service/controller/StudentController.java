package com.kgisl.student_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kgisl.student_service.model.Course;
import com.kgisl.student_service.model.CourseResponse;
import com.kgisl.student_service.model.Student;
import com.kgisl.student_service.model.Studentwrapper;
import com.kgisl.student_service.service.StudentService;


@RestController
@RequestMapping("/student")
@CrossOrigin(origins ="http://localhost:4200")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // CRUD operation
    @GetMapping("/getall")
    public ResponseEntity<List<Student>> getAllStudent() {
        return studentService.getAllStudent();
    }

    @GetMapping("get/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Studentwrapper student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id,@RequestBody Student student){
        return studentService.updateStudent(id,student);
    }

    // CRUD operations

    
    // gives all courses based on student
    @GetMapping("/getcourse/{id}")
    public ResponseEntity<List<Course>> getCourseByStudent(@PathVariable long id){
    	List<Course> courses = studentService.getCourseByStudent(id);
        return new ResponseEntity<>(courses,HttpStatus.ACCEPTED);
    }
    
    //to get elective based on yearOfsstudy
    @GetMapping("/get/e-course/{yearOfStudy}")
    public ResponseEntity<List<Course>> getElectiveCourses(@PathVariable int yearOfStudy){
    	return new ResponseEntity<>(studentService.getElectiveCourse(yearOfStudy),HttpStatus.ACCEPTED);
    }
    
    //to check elective courses selected or not
    @PostMapping("check/e-course/{yearOfStudy}")
    public ResponseEntity<Boolean> checkE_courseForStudent(@RequestBody List<Integer> courses,@PathVariable int yearOfStudy){
    	boolean check = studentService.checkE_courseForStudent(courses,yearOfStudy);
    	return new ResponseEntity<>(check,HttpStatus.ACCEPTED);
    }
    // to save the student selected elective course
    @PostMapping("submit")
    public ResponseEntity<String> selectElectiveCourse(@RequestBody CourseResponse courseResponse){
    	System.out.println(courseResponse);
        return studentService.selectElectiveCourse(courseResponse);
        
    
    } 
}
