package com.kgisl.course_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kgisl.course_service.model.Course;
import com.kgisl.course_service.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<List<Course>> getAllCourse() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.ACCEPTED);
    }

    // CRUD opertion
    public ResponseEntity<String> createCourse(Course course) {

        Optional<Course> existingCourse = courseRepository.findByName(course.getName());

        if (existingCourse.isPresent()) {
            return new ResponseEntity<>("Course already exists", HttpStatus.BAD_REQUEST);
        } else {
            courseRepository.save(course);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Course> getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    public ResponseEntity<Course> updateCourse(Long id, Course updatedcourse) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            updatedcourse.setId(course.getId());
            courseRepository.save(updatedcourse);
            return new ResponseEntity<>(course, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(course, HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<String> deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            courseRepository.deleteById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
    // CRUD opertion

    // generate courseIds For new Student
    public List<Integer> getNormalCourseIds(String dept, int yearOfStudy) {
        List<Integer> courseIds = courseRepository.findCourseIdsByDeptAndYear(dept, yearOfStudy);
        return courseIds;
    }

    // generate courseIds For old Student
    public List<Integer> getNormalCourseIdsUpdate(String dept, int yearOfStudy) {
        List<Integer> courseIds = courseRepository.findCourseIdsByDeptAndYearForUpdate(dept, yearOfStudy);
        return courseIds;
    }

    // generate Random elective Courses (Not for current year student)
    public Integer getElectiveCourseId(int year) {
        return courseRepository.findRandomElectiveByYear(year);
    }

    // generate elective Courses based on yearOfStudy
    public List<Course> getAllElectiveCourses(int yearOfStudy) {
    	List<Course> courses = courseRepository.findAllElectiveByYear(yearOfStudy);
        return courses;
    }

    // gives all course based on student
    public List<Course> getAllCourseByStudent(List<Integer> courseIds) {
        List<Course> courses = new ArrayList<>();
        for (long id : courseIds) {
            courses.add(courseRepository.findById(id).get());
        }

        return courses;

    }

    // to check elective course, already registered by student in year of study
    public Boolean checke_CourseByStudent(List<Integer> courseIds, int yearOfStudy) {
        List<Course> courses = getAllCourseByStudent(courseIds);
        Boolean check = false;
        for (Course c : courses) {
            if (c.getType().equalsIgnoreCase("Elective") && c.getYearOfStudy().equals(yearOfStudy)) {
                check = true;
                return check;
            }
        }
        return check;
    }

}
