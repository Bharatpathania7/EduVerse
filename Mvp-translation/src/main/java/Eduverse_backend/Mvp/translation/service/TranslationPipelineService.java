package Eduverse_backend.Mvp.translation.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TranslationPipelineService {
    @Async
    public void runTranslationPipeline(String s3Url , String videoId){
        System.out.println("Running translation for :" + videoId + "at" + s3Url);
    }
}
