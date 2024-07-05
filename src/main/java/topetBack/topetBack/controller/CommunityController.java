package topetBack.topetBack.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.form.CommunityForm;
import topetBack.topetBack.form.CommunityPostForm;
import topetBack.topetBack.repository.TestRepository;
import topetBack.topetBack.repository.ToPetCommunityRepository;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {
	
    private final ToPetCommunityRepository toPetCommunityRepository;
    
    @PostMapping("/api/community/community/post")
    public void communityTest(@RequestBody CommunityPostForm communityPostForm){
    	toPetCommunityRepository.save(communityPostForm);
    }

//    @PostMapping("/api/community/community/post")
//    public String comm5unityPostHome(@RequestBody CommunityForm communityForm) {
//		return "?" + communityForm;
//    	
//    }
    
    
    @PostMapping(value = "/api/community/community/postPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String communityPostPhoto(@RequestPart(value = "photos", required = true) MultipartFile[] photos) {
        if (photos.length > 10) {
            return "최대 10개의 사진만 업로드할 수 있습니다.";
        }

        for (MultipartFile photo : photos) {
            // 각 파일에 대해 원하는 작업 수행
            System.out.println("파일 이름: " + photo.getOriginalFilename());
         // JSON 객체 생성
	        JsonObject jsonObject = new JsonObject();
	        // 이미지 파일이 저장될 경로 설정
	        String fileRoot  = "C:\\DevBack/image/";
	        // 업로드된 파일의 원본 파일명과 확장자 추출
	        String originalFileName = photo.getOriginalFilename();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	        // 새로운 파일명 생성 (고유한 식별자 + 확장자)
	        String savedFileName = UUID.randomUUID() + extension;
	        // 저장될 파일의 경로와 파일명을 나타내는 File 객체 생성
	        File targetFile = new File(fileRoot + savedFileName);

	        try {
	            // 업로드된 파일의 InputStream 얻기
	            java.io.InputStream fileStream = photo.getInputStream();
	            // 업로드된 파일을 지정된 경로에 저장
	            FileCopyUtils.copy(fileStream, new FileOutputStream(targetFile));

	            // JSON 객체에 이미지 URL과 응답 코드 추가
	            jsonObject.addProperty("DevBack", "/image/" + savedFileName);
	            jsonObject.addProperty("responseCode", "success");
	        } catch (IOException e) {
	            // 파일 저장 중 오류가 발생한 경우 해당 파일 삭제 및 에러 응답 코드 추가
	            if (targetFile.exists()) {
	                targetFile.delete();
	            }
	            jsonObject.addProperty("responseCode", "error");
	            e.printStackTrace();
	        }
	        // JSON 객체를 문자열로 변환하여 반환
	        String result = jsonObject.toString();
	          
        }
        return "성공";
    }
}
