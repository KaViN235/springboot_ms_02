package com.kgisl.course_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kgisl.course_service.model.Course;
import com.kgisl.course_service.service.CourseService;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins ="http://localhost:4200")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // CRUD opertion
    @GetMapping("getall")
    public ResponseEntity<List<Course>> getAllCourse() {
        return courseService.getAllCourse();
    }

    @GetMapping("get/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {

        return courseService.createCourse(course);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        return courseService.deleteCourse(id);
    }
    // CRUD opertion


    //generate courseIds For new Student
    @GetMapping("/generate/new")
    public List<Integer> getNormalCourseIds(@RequestParam String dept,@RequestParam int yearOfStudy){
        return courseService.getNormalCourseIds(dept,yearOfStudy);
    }
    
   //generate courseIds For old Student
    @GetMapping("/generate/update")
    public List<Integer> getNormalCourseIdsUpdate(@RequestParam String dept,@RequestParam int yearOfStudy){
        return courseService.getNormalCourseIdsUpdate(dept,yearOfStudy);
    }


    //generate Random elective Courses (Not for current year student)
    @GetMapping("get/e-courseIdforStudent/{yearOfStudy}")
    public Integer getElectiveCourseId(@PathVariable int yearOfStudy){
        return courseService.getElectiveCourseId(yearOfStudy);
    }

    //generate elective Courses based on yearOfStudy
    @GetMapping("/get/e-courseByStudent/{yearOfStudy}")
    public List<Course> getAllElectiveCourses(@PathVariable int yearOfStudy){
        return courseService.getAllElectiveCourses(yearOfStudy);
    }

    // gives all courses based on student
    @PostMapping("getByStudent/courseforStudent")
    public List<Course> getAllCourseByStudent(@RequestBody List<Integer> courseIds){
        return courseService.getAllCourseByStudent(courseIds);
    }

     //to check elective course, already registered by student in  year of study
     @PostMapping("getByStudent/check/e-courseforStudent/{yearOfStudy}")
     public Boolean checke_CourseByStudent(@RequestBody List<Integer> courseIds,@PathVariable int yearOfStudy){
         return courseService.checke_CourseByStudent(courseIds,yearOfStudy);
     }

}
