package Eduverse_backend.Mvp.translation.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class S3Service {
    @Value("${aws.bucket-name}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public S3Service(@Value("${aws.access-key}") String accessKey,
                     @Value("${aws.secret-key}") String secretKey,
                     @Value("${aws.region}") String region) {
        BasicAWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
    }

    public String generatePresignedUrl(String fileName, String teacherEmail) {
        String key = "videos/" + teacherEmail + "/" + System.currentTimeMillis() + "_" + fileName;

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10); // 10 min
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        return amazonS3.generatePresignedUrl(request).toString();
    }
}
