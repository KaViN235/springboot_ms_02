package com.kgisl.student_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.kgisl.student_service.model.Course;

@FeignClient("COURSE-SERVICE")
public interface CourseInterface {

    @GetMapping("course/generate/new")
    public List<Integer> getNormalCourseIds(@RequestParam String dept, @RequestParam int yearOfStudy);

    @GetMapping("course/get/e-courseIdforStudent/{yearOfStudy}")
    public Integer getElectiveCourseId(@PathVariable int yearOfStudy);

    @PostMapping("course/getByStudent/courseforStudent")
    public List<Course> getAllCourseByStudent(@RequestBody List<Integer> courseIds);

    @GetMapping("course/get/e-courseByStudent/{yearOfStudy}")
    public List<Course> getAllElectiveCourses(@PathVariable int yearOfStudy);

    @PostMapping("course/getByStudent/check/e-courseforStudent/{yearOfStudy}")
    public Boolean checke_CourseByStudent(@RequestBody List<Integer> courseIds,
            @PathVariable int yearOfStudy);
    
    
 

}
