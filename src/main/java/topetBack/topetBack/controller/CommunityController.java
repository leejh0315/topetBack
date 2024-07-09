package topetBack.topetBack.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.domain.Image;
import topetBack.topetBack.form.CommunityPostForm;
import topetBack.topetBack.repository.ImageRepository;
import topetBack.topetBack.repository.ToPetCommunityRepository;
import topetBack.topetBack.service.ToPetCommunityService;
import topetBack.topetBack.validation.CommunityVaildator;

@RestController
@RequiredArgsConstructor
@Slf4j
@Controller
public class CommunityController {
	
    private final ToPetCommunityRepository toPetCommunityRepository;
    private final CommunityVaildator vaildator;
    
    @Autowired
	private ToPetCommunityService toPetCommunityService;
	
    
	@InitBinder
	public void init(WebDataBinder  webDataBinder) {
		webDataBinder.addValidators(vaildator);
	}
	
	//커뮤니티 글 작성
    @PostMapping("/api/community/community/post")
    public Object communityTest(@RequestBody CommunityPostForm communityPostForm ,BindingResult bindingResult){
    	
    	vaildator.validate(communityPostForm, bindingResult);
    	log.info(bindingResult.toString());
    	
    	if(bindingResult.hasErrors()) {
            return bindingResult.getFieldErrors();
        }
    	
    	toPetCommunityRepository.save(communityPostForm);
        return "success";
        
    }
    
    
    @GetMapping("/api/post_id/{communityId}")
    public String getPostByPostId(
          @PathVariable("communityId") Long communityId, HttpServletRequest req) {
    	  
    	Optional<CommunityPostForm> c = toPetCommunityRepository.findById(communityId);
    	  System.out.println(c);
	 return "";
	 }
    
    private final ImageRepository imageRepository;

    //파일 저장
    @PostMapping(value = "/api/community/community/postPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String communityPostPhoto(@RequestPart(value = "photos", required = true) MultipartFile[] photos) {
        if (photos.length > 10) {
            return "최대 10개의 사진만 업로드할 수 있습니다.";
        }
 
//        Map photoMap = new HashMap<Integer , List<String>>();
        List<Image> imageList = new ArrayList<Image>();
        
       
        for (int i = 0; i < photos.length; i++) {
        		
         // JSON 객체 생성
	        JsonObject jsonObject = new JsonObject();
	        // 이미지 파일이 저장될 경로 설정
	        String fileRoot  = "C:\\DevBack/image/";
	        // 업로드된 파일의 원본 파일명과 확장자 추출
	        String originalFileName = photos[i].getOriginalFilename();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	        // 새로운 파일명 생성 (고유한 식별자 + 확장자)
	        String savedFileName = UUID.randomUUID() + extension;
	        // 저장될 파일의 경로와 파일명을 나타내는 File 객체 생성
	        File targetFile = new File(fileRoot + savedFileName);

	        try {
	            // 업로드된 파일의 InputStream 얻기
	            java.io.InputStream fileStream = photos[i].getInputStream();
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
	        Image tempImage = new Image(null ,fileRoot, savedFileName);
	        imageList.add(tempImage);
	        // JSON 객체를 문자열로 변환하여 반환
	        String result = jsonObject.toString();
	        System.out.println(imageList.get(i));
        }
        imageRepository.saveAll(imageList);
        return "성공";
    }
    
    //게시판 리스트
    @GetMapping("/api/community/{animal}/{category}")
    public String boardList(Model model, @PathVariable("animal")String animal, @PathVariable("category")String category
    	) {
    	System.out.println(animal + category);
    	System.out.println("test" + toPetCommunityService.getCommunityPreviewByType(animal, category).toString());
    	return animal + " " + category;
    }
    
    @GetMapping("/api/community/list")
    public String list() {
    	System.out.println(toPetCommunityService.findAll());
    	return "";
    }

    
    //게시물 삭제
    @GetMapping("/api/community/delete/{postId}")
    public String deleteCommunity(@PathVariable("postId") Long postId) {
        toPetCommunityService.deleteCommunity(postId);
        System.out.println("삭제 게시물 번호: " + postId);
        
        return "삭제 게시물 번호: " + postId;
    }
    
}
