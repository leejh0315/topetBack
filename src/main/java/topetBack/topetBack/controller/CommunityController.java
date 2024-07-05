package topetBack.topetBack.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.form.CommunityPostForm;
import topetBack.topetBack.repository.TestRepository;
import topetBack.topetBack.repository.ToPetCommunityRepository;

@RestController
@Slf4j
public class CommunityController {
	
    @PostMapping("/api/community/community/post")
    public String communityPostHome(@RequestBody CommunityPostForm communityPostForm) {
		return "?" + communityPostForm;
    	
    }    
    
    @PostMapping(value = "/api/community/community/postPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String communityPostPhoto(@RequestPart(value = "photos", required = true) MultipartFile[] photos) {
        if (photos.length > 10) {
            return "최대 10개의 사진만 업로드할 수 있습니다.";
        }

        for (MultipartFile photo : photos) {
            // 각 파일에 대해 원하는 작업 수행
            System.out.println("파일 이름: " + photo.getOriginalFilename());
        }
        return "성공";
    }
}
