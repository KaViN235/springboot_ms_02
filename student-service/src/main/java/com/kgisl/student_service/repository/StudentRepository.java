package com.kgisl.student_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.kgisl.student_service.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value ="SELECT s.student_id FROM student s WHERE s.department = :dept AND s.student_id LIKE CONCAT(:yearId,'%') ORDER BY s.student_id DESC LIMIT 1",
    nativeQuery = true)
    String findPreviousStudentIdByDeptandYear(String dept, Integer yearId);

}
