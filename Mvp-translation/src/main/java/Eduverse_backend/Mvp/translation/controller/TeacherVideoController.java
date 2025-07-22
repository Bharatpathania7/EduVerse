//package Eduverse_backend.Mvp.translation.controller;
//
//import Eduverse_backend.Mvp.translation.model.VideoMetadata;
//import Eduverse_backend.Mvp.translation.repository.VideoRepository;
//import Eduverse_backend.Mvp.translation.service.S3Service;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.domain.Page;
////import org.springframework.data.domain.PageRequest;
////import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.security.Principal;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TeacherVideoController {
//    @Autowired
//    private S3Service s3Service;
//    @Autowired
//    private VideoRepository videoRepository;
//                                            //This endpoint generates a pre-signed S3 URL that the frontend can use to upload a large video file directly to Amazon S3, without the video passing through your Spring Boot server.
//    @GetMapping("/s3/upload-url")
//    public ResponseEntity<Map<String,String>> getPresignedUrl(
//            @RequestParam String fileName,
//            Principal principal
//    ){
//        String email = principal.getName();
//        String key = "videos/" + email + "/" + System.currentTimeMillis() + "_" + fileName;
//        String url = s3Service.generatePresignedUrl(fileName, email);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("uploadUrl", url);
//        response.put("s3Key", key);
//
//        return ResponseEntity.ok(response);
//    }
//    @PostMapping("/save-metadata")
//    public  ResponseEntity<?> saveMetadata(@RequestBody VideoMetadata metadata , Principal principal){
//        metadata.setUploadedBy(principal.getName());
//        metadata.setUploadDate(LocalDate.now());
//        metadata.setStatus("Uploaded");
//        videoRepository.save(metadata);
//        return ResponseEntity.ok("Metadata Saved Sucesfully");
//    }
//   @GetMapping("/videos")
//   public ResponseEntity<?> getMyVideos(
//           @RequestParam(defaultValue = "0") int page,
//           @RequestParam(defaultValue = "10") int size,
//           @RequestParam(defaultValue = "uploadDate") String sortBy,
//           @RequestParam(defaultValue = "desc") String order,
//           Principal principal
//   ){
//       String email =  principal.getName();
//       Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//       Pageable pageable = PageRequest.of(page,size,sort);
//       Page<VideoMetadata> videos = videoRepository.findByUploadedBy(email, pageable);
//       return ResponseEntity.ok(videos);
//   }
//   @GetMapping("/videos/search/title")
//    public  ResponseEntity<?> searchByTitle(
//            @RequestParam String Keyword,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam( defaultValue = "10") int size
//   ){
//        Pageable pageable = PageRequest.of(page,size);
//        Page<VideoMetadata> videos = videoRepository.findByTitleContainingIgnoreCase(keyword , pageable);
//   }
//}
package Eduverse_backend.Mvp.translation.controller;

import Eduverse_backend.Mvp.translation.model.VideoMetadata;
import Eduverse_backend.Mvp.translation.repository.VideoRepository;
import Eduverse_backend.Mvp.translation.service.S3Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherVideoController {

    @Autowired private S3Service s3Service;
    @Autowired private VideoRepository videoRepo;

    // 1️⃣ Generate pre-signed S3 upload URL
    @GetMapping("/s3/upload-url")
    public ResponseEntity<Map<String, String>> getPresignedUrl(
            @RequestParam String fileName,
            Principal principal
    ) {
        String email = principal.getName();
        String key = "videos/" + email + "/" + System.currentTimeMillis() + "_" + fileName;
        String url = s3Service.generatePresignedUrl(fileName, email);

        Map<String, String> response = new HashMap<>();
        response.put("uploadUrl", url);
        response.put("s3Key", key);

        return ResponseEntity.ok(response);
    }

    // 2️⃣ Save metadata after video upload
    @PostMapping("/save-metadata")
    public ResponseEntity<?> saveMetadata(@RequestBody VideoMetadata metadata, Principal principal) {
        metadata.setUploadedBy(principal.getName());
        metadata.setUploadDate(LocalDate.now());
        metadata.setStatus("uploaded");
        videoRepo.save(metadata);
        return ResponseEntity.ok("Metadata saved successfully");
    }

    // 3️⃣ Get paginated list of teacher's videos
    @GetMapping("/videos")
    public ResponseEntity<?> getMyVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uploadDate") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            Principal principal
    ) {
        String email = principal.getName();
        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VideoMetadata> videos = videoRepo.findByUploadedBy(email, pageable);
        return ResponseEntity.ok(videos);
    }

    // 4️⃣ Search by title
    @GetMapping("/videos/search/title")
    public ResponseEntity<?> searchByTitle(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoMetadata> videos = videoRepo.findByTitleContainingIgnoreCase(keyword, pageable);
        return ResponseEntity.ok(videos);
    }

    // 5️⃣ Search by tags
    @GetMapping("/videos/search/tags")
    public ResponseEntity<?> searchByTags(
            @RequestParam List<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoMetadata> videos = videoRepo.findByTagsIn(tags, pageable);
        return ResponseEntity.ok(videos);
    }

    // 6️⃣ Filter by subject
    @GetMapping("/videos/filter/subject")
    public ResponseEntity<?> filterBySubject(
            @RequestParam String subject,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoMetadata> videos = videoRepo.findBySubject(subject, pageable);
        return ResponseEntity.ok(videos);
    }

    // 7️⃣ Filter by classInfo and status
    @GetMapping("/videos/filter/class-status")
    public ResponseEntity<?> filterByClassAndStatus(
            @RequestParam String classInfo,
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoMetadata> videos = videoRepo.findByClassInfoAndStatus(classInfo, status, pageable);
        return ResponseEntity.ok(videos);
    }
}
