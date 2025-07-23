package Eduverse_backend.Mvp.translation.controller;

import Eduverse_backend.Mvp.translation.model.Comment;
import Eduverse_backend.Mvp.translation.model.Teacher;
import Eduverse_backend.Mvp.translation.model.VideoMetadata;
import Eduverse_backend.Mvp.translation.repository.CommentRepository;
import Eduverse_backend.Mvp.translation.repository.TeacherRepository;
import Eduverse_backend.Mvp.translation.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentVideoController {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CommentRepository commentRepo;

    @GetMapping("/videos/today")
    public ResponseEntity<?> getTodayVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("UploadDate").descending());
        LocalDate today = LocalDate.now();
        Page<VideoMetadata> videos = videoRepository.findByUploadDate(today, pageable);
        return ResponseEntity.ok(videos);
    }
       @GetMapping("/videos/{id}")
    public  ResponseEntity<?> getVideoDetail(@PathVariable String id){
           Optional<VideoMetadata> VideoOpt = videoRepository.findById(id);
           if(VideoOpt.isEmpty()){
               return  ResponseEntity.status(404).body("Video Not Found");
           }
           VideoMetadata video = VideoOpt.get();

           Optional<Teacher> teacherOpt = teacherRepository.findByEmail(video.getUploadedBy());

           Map<String,Object> response = new HashMap<>();
           response.put("Video" , video);
           response.put("teacher" , teacherOpt.orElse(null));
           response.put("translations", List.of("English" ,"Hindi" ,"Punjabi"));
           response.put("comments" , List.of());
           return  ResponseEntity.ok(response);
       }
       @PostMapping("/comments")
    public  ResponseEntity<?> addcomment (@RequestBody Comment comment , Principal principal){
        comment.setCommenterEmail(principal.getName());
        comment.setTimestap(LocalDateTime.now());
        commentRepo.save(comment);
        return ResponseEntity.ok("Comment added successfuly");
       }

       @GetMapping("/comments/{videoId}")
    public  ResponseEntity<List<Comment>> getComments(@PathVariable String videoId){
        List<Comment> comments = commentRepo.findByVideoIdOrderByTimestampDesc(videoId);
        return ResponseEntity.ok(comments);
       }
}
