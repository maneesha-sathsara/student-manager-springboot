package com.example.student_manager.service;
import com.example.student_manager.model.Student;
import com.example.student_manager.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class StudentService {
 private final StudentRepository repository;
 public StudentService(StudentRepository repository) {
 this.repository = repository;
 }
 public List<Student> findAll() {
 return repository.findAll();
 }
 public Optional<Student> findById(Long id) {
 return repository.findById(id);
 }
 public Student save(Student student) {
 return repository.save(student);
 }
 public void deleteById(Long id) {
 repository.deleteById(id);
 }
 public boolean emailExists(String email, Long excludeId) {
 return excludeId == null
 ? repository.existsByEmail(email)
 : repository.existsByEmailAndIdNot(email, excludeId);
 }
}