package com.kgisl.student_service.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kgisl.student_service.feign.CourseInterface;
import com.kgisl.student_service.model.Course;
import com.kgisl.student_service.model.CourseResponse;
import com.kgisl.student_service.model.Student;
import com.kgisl.student_service.model.Studentwrapper;
import com.kgisl.student_service.repository.StudentRepository;
import org.springframework.http.HttpStatus;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseInterface courseInterface;

    // CRUD opertion
    public ResponseEntity<List<Student>> getAllStudent() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // create student with three methods -> mappingToStudent() generateStudentId()
    // findYearOfStudy()
    public Student createStudent(Studentwrapper studentwrapper) {
        Student student = new Student();
        mappingToStudent(studentwrapper, student);
        return studentRepository.save(student);
    }

    private void mappingToStudent(Studentwrapper studentwrapper, Student student) {

        student.setName(studentwrapper.getName());
        student.setBirthdate(studentwrapper.getBirthdate());
        student.setAddress(studentwrapper.getAddress());
        student.setPhone_number(studentwrapper.getPhone_number());
        student.setDepartment(studentwrapper.getDepartment());
        student.setDateOfJoining(studentwrapper.getDateOfJoining());

        String studentId = generateStudentId(student.getDateOfJoining(), student.getDepartment());
        student.setStudentId(studentId);

        String email = studentId.toLowerCase() + "@doors.com";
        student.setEmail(email);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        student.setPassword(student.getBirthdate().format(formatter).replace("-", ""));

        if (!student.getIsYearCompleted()) {
            findYearOfStudy(student);
        }
        // generate courseIds For new Student
        student.setCourseIds(courseInterface.getNormalCourseIds(student.getDepartment(), student.getYearOfStudy()));

        int current_year = student.getYearOfStudy();

        if (student.getIsYearCompleted())
            current_year++;

        while (current_year > 1) {
            current_year--;
            // generate Random elective Courses (Not for current year student)
            Integer elective_courseId = courseInterface.getElectiveCourseId(current_year);
            student.getCourseIds().add(elective_courseId);
        }
        System.out.println(student.getCourseIds());

    }

    private String generateStudentId(LocalDate date, String Dept) {

        Integer yearId = (date.getYear()) % 100;
        String deptId = Dept.toUpperCase();

        String previousStudentId = studentRepository.findPreviousStudentIdByDeptandYear(Dept, yearId);
        int nextId = 1;

        if (previousStudentId != null) {

            String previousFormatId = previousStudentId.substring(previousStudentId.length() - 3);
            nextId = Integer.parseInt(previousFormatId) + nextId;
        }

        String formatId = String.format("%03d", nextId);
        return yearId + deptId + formatId;

    }

    private void findYearOfStudy(Student student) {

        LocalDate currentDate = LocalDate.now();
        int yearsPassed = Period.between(student.getDateOfJoining(), currentDate).getYears();

        if (yearsPassed == 0) {
            student.setYearOfStudy(1);
            return;
        }
        // 5 denotes may month
        if (currentDate.getMonthValue() > 5) {

            yearsPassed += 1;
            if (yearsPassed >= 5) {
                student.setYearOfStudy(4);
                student.setIsYearCompleted(true);
                return;
            }

        }
        student.setYearOfStudy(yearsPassed);

    }

    public ResponseEntity<String> updateStudent(Long id, Student student) {
        Optional<Student> s = studentRepository.findById(id);
        if (s.isPresent()) {
            student.setId(s.get().getId());
            studentRepository.save(student);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);

    }

    // CRUD opertion

    // gives all courses based on student
    public List<Course> getCourseByStudent(long id) {
        Student student = studentRepository.findById(id).get();
        return courseInterface.getAllCourseByStudent(student.getCourseIds());
    }

    // to save the student selected elective course
    public ResponseEntity<String> selectElectiveCourse(CourseResponse courseResponse) {
        Optional<Student> student = studentRepository.findById(courseResponse.getStudId());
        if (student.isPresent()) {

            if (courseInterface.checke_CourseByStudent(student.get().getCourseIds(), student.get().getYearOfStudy())) {
                return new ResponseEntity<>("Already Registered", HttpStatus.ALREADY_REPORTED);
            }

            student.get().getCourseIds().add(courseResponse.getCourseId());
            studentRepository.save(student.get());
            return new ResponseEntity<>("Registered Successfully", HttpStatus.ACCEPTED);

        }
        return new ResponseEntity<>("Student Not Found", HttpStatus.NOT_FOUND);
    }

	public List<Course> getElectiveCourse(int yearOfStudy) {
		return courseInterface.getAllElectiveCourses(yearOfStudy);
	}

	public Boolean checkE_courseForStudent(List<Integer> courses, int yearOfStudy) {
		
		return courseInterface.checke_CourseByStudent(courses, yearOfStudy);
	}

	
}
