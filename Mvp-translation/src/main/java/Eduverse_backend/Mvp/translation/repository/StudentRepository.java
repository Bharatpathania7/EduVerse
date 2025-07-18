package Eduverse_backend.Mvp.translation.repository;

import Eduverse_backend.Mvp.translation.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository <Student , String>{
    Optional<Student> findByEmail(String email);
    Optional<Student> findBystudentGuid(String studentGuId );
}
