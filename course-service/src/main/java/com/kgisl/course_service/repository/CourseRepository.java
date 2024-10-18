package com.kgisl.course_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kgisl.course_service.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

    @Query(value = "SELECT c.id FROM Course c WHERE c.department = :dept AND c.year_of_study <= :year AND c.type = 'Normal'", nativeQuery = true)
    List<Integer> findCourseIdsByDeptAndYear(String dept, int year);

    @Query(value = "SELECT c.id FROM Course c WHERE c.department = :dept AND c.year_of_study = :year AND c.type = 'Normal'", nativeQuery = true)
    List<Integer> findCourseIdsByDeptAndYearForUpdate(String dept, int year);

    @Query(value = "SELECT c.id FROM Course c WHERE c.year_of_study = :year AND c.type = 'Elective' ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Integer findRandomElectiveByYear(int year);

    
    @Query(value = "SELECT * FROM Course c WHERE c.year_of_study = :year AND c.type = 'Elective'", nativeQuery = true)
    List<Course> findAllElectiveByYear(int year);

}
