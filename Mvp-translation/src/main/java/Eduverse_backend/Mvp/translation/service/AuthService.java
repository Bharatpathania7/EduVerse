package Eduverse_backend.Mvp.translation.service;

import Eduverse_backend.Mvp.translation.dto.UserLoginRequest;
import Eduverse_backend.Mvp.translation.model.Teacher;
import Eduverse_backend.Mvp.translation.model.Student;
import Eduverse_backend.Mvp.translation.repository.TeacherRepository;
import Eduverse_backend.Mvp.translation.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtService jwtService;

    public String login(UserLoginRequest request) {
        String id = request.getIdentifier();
        String password = request.getPassword();

        // Check teacher by email or teacherId
        Teacher teacher = teacherRepository.findByEmail(id)
                .orElseGet(() -> teacherRepository.findByteacherId(id).orElse(null));

        if (teacher != null && teacher.getpassword().equals(password)) {
            return jwtService.generateToken(teacher.getEmail());
        }

        // Check student by email or studentGuid
        Student student = studentRepository.findByEmail(id)
                .orElseGet(() -> studentRepository.findBystudentGuid(id).orElse(null));

        if (student != null && student.getPassword().equals(password)) {
            return jwtService.generateToken(student.getEmail());
        }

        throw new RuntimeException("Invalid credentials");
    }
}
