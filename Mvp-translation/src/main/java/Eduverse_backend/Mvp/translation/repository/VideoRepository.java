package Eduverse_backend.Mvp.translation.repository;

import Eduverse_backend.Mvp.translation.model.VideoMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface VideoRepository extends MongoRepository<VideoMetadata , String> {
    // ✅ All videos by teacher, with pagination & sorting
    Page<VideoMetadata> findByUploadedBy(String uploadedBy, Pageable pageable);

    // ✅ All videos by subject, paginated
    Page<VideoMetadata> findBySubject(String subject, Pageable pageable);

    // ✅ Search by title containing keyword (case-insensitive)
    Page<VideoMetadata> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // ✅ Search by tags (tag list contains at least one match)
    Page<VideoMetadata> findByTagsIn(List<String> tags, Pageable pageable);

    // ✅ (Optional) Filter by class and status
    Page<VideoMetadata> findByClassInfoAndStatus(String classInfo, String status, Pageable pageable);

    Page<VideoMetadata> findByUploadDate(LocalDate uploadDate , Pageable pageable);
    Page<VideoMetadata> findByTitleContainingIgnoreCaseAndStatus(String title, String status, Pageable pageable);
    Page<VideoMetadata> findByTagsInAndStatus(List<String> tags, String status, Pageable pageable);
    Page<VideoMetadata> findByUploadedByInAndStatus(List<String> uploadedBy, String status, Pageable pageable);
    Page<VideoMetadata> findBySubjectAndStatus(String subject, String status, Pageable pageable);
    Page<VideoMetadata> findByUploadDateAndStatus(LocalDate uploadDate, String status, Pageable pageable);


}
