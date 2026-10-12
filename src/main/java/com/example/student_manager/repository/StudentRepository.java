package com.example.student_manager.repository;
import com.example.student_manager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student, Long> {
 boolean existsByEmail(String email);
 boolean existsByEmailAndIdNot(String email, Long id);
}
