package Eduverse_backend.Mvp.translation.repository;

import Eduverse_backend.Mvp.translation.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeacherRepository extends MongoRepository<Teacher , String> {
    Optional<Teacher> findByEmail(String email);
 Optional<Teacher> findByteacherId( String teacherId);
}
