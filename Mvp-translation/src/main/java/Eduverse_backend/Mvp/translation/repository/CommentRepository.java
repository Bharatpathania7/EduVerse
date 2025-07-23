package Eduverse_backend.Mvp.translation.repository;

import Eduverse_backend.Mvp.translation.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByVideoIdOrderByTimestampDesc(String videoId);

}
