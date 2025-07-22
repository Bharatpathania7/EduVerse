package Eduverse_backend.Mvp.translation.controller;

import Eduverse_backend.Mvp.translation.dto.UserLoginRequest;
import Eduverse_backend.Mvp.translation.repository.StudentRepository;
import Eduverse_backend.Mvp.translation.repository.TeacherRepository;
import Eduverse_backend.Mvp.translation.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
        String email = principal.getName(); // Extracted from JWT

        return teacherRepository.findByEmail(email)
                .<ResponseEntity<?>>map(teacher -> {
                    teacher.setpassword(null);
                    return ResponseEntity.ok(teacher);
                })
                .orElseGet(() -> studentRepository.findByEmail(email)
                        .<ResponseEntity<?>>map(student -> {
                            student.setPassword(null);
                            return ResponseEntity.ok(student);
                        })
                        .orElseGet(() -> ResponseEntity.status(404).body("User not found"))
                );
    }


}
