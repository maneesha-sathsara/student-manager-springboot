package com.example.student_manager.controller;
import com.example.student_manager.model.Student;
import com.example.student_manager.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
@Controller
public class StudentController {
 private static final List<String> COURSES = List.of(
 "Computer Science", "Software Engineering", "Data Science",
 "Statistics", "Information Systems");
 private final StudentService service;
 public StudentController(StudentService service) {
 this.service = service;
 }
 @ModelAttribute("courses")
 public List<String> courses() {
 return COURSES;
 }
 @GetMapping("/")
 public String home() {
 return "redirect:/students";
 }
 // READ: show the form and the table of all students
 @GetMapping("/students")
 public String list(Model model) {
 model.addAttribute("student", new Student());
 model.addAttribute("students", service.findAll());
 return "students";
 }
 // CREATE: save a new student
 @PostMapping("/students")
 public String create(@Valid @ModelAttribute("student") Student student,
 BindingResult result, Model model,
 RedirectAttributes redirect) {
 if (service.emailExists(student.getEmail(), null)) {
 result.rejectValue("email", "duplicate", "This email is already registered");
 }
 if (result.hasErrors()) {
 model.addAttribute("students", service.findAll());
 return "students";
 }
 service.save(student);
 redirect.addFlashAttribute("success", "Student added successfully.");
 return "redirect:/students";
 }
 // UPDATE (step 1): show the edit form filled with existing data
 @GetMapping("/students/{id}/edit")
 public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirect) {
 return service.findById(id)
 .map(s -> {
 model.addAttribute("student", s);
 return "edit-student";
 })
 .orElseGet(() -> {
 redirect.addFlashAttribute("error", "Student not found.");
 return "redirect:/students";
 });
 }
 // UPDATE (step 2): save the changes
 @PostMapping("/students/{id}/update")
 public String update(@PathVariable Long id,
 @Valid @ModelAttribute("student") Student student,
 BindingResult result, RedirectAttributes redirect) {
 student.setId(id);
 if (service.emailExists(student.getEmail(), id)) {
 result.rejectValue("email", "duplicate", "This email is already registered");
 }
 if (result.hasErrors()) {
 return "edit-student";
 }
 service.save(student);
 redirect.addFlashAttribute("success", "Student updated successfully.");
 return "redirect:/students";
 }
 // DELETE
 @PostMapping("/students/{id}/delete")
 public String delete(@PathVariable Long id, RedirectAttributes redirect) {
 service.deleteById(id);
 redirect.addFlashAttribute("success", "Student deleted successfully.");
 return "redirect:/students";
 }
}
