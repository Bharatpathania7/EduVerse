package Eduverse_backend.Mvp.translation.service;


import Eduverse_backend.Mvp.translation.dto.UserLoginRequest;
import Eduverse_backend.Mvp.translation.model.Student;
import Eduverse_backend.Mvp.translation.model.Teacher;
import Eduverse_backend.Mvp.translation.repository.StudentRepository;
import Eduverse_backend.Mvp.translation.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    public Object Login(UserLoginRequest request) {
//        String id = request.getIdentifier();
//        String password = request.getPassword();
//
// // teacher check
//        Teacher teacher = teacherRepository.findByEmail(id)
//                .orElseGet(() -> teacherRepository.findByteacherId(id).orElse(null));
//          if(teacher!= null && teacher.getPassword().equals(password)){
//              teacher.setPassword(null);
//             return teacher;
//          }

    /// / student check
//
//        Student student = studentRepository.findByEmail(id)
//                .orElseGet(() -> studentRepository.findBystudentGuid(id).orElse(null));
//        if (student != null && student.getPassword().equals(password)) {
//             student.setPassword(null);
//              return  student;
//
//        }
//                      throw  new RuntimeException("Invalid Credentials");
//        }
//
//
//}
    public Object Login(UserLoginRequest request) {
        String id = request.getIdentifier();
        String password = request.getPassword();

        System.out.println("Trying login for ID: " + id);

        Teacher teacher = teacherRepository.findByEmail(id)
                .orElseGet(() -> teacherRepository.findByteacherId(id).orElse(null));
        if (teacher != null) {
            System.out.println("Found teacher: " + teacher.getEmail());
            if (teacher.getPassword().equals(password)) {
                teacher.setPassword(null);
                return teacher;
            } else {
                System.out.println("Password mismatch for teacher");
            }
        } else {
            System.out.println("Teacher not found");
        }

        Student student = studentRepository.findByEmail(id)
                .orElseGet(() -> studentRepository.findBystudentGuid(id).orElse(null));
        if (student != null) {
            System.out.println("Found student: " + student.getEmail());
            if (student.getPassword().equals(password)) {
                student.setPassword(null);
                return student;
            } else {
                System.out.println("Password mismatch for student");
            }
        } else {
            System.out.println("Student not found");
        }

        throw new RuntimeException("Invalid Credentials");
    }
}

