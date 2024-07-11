package topetBack.topetBack.schedule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.schedule.dao.ScheduleRepository;
import topetBack.topetBack.schedule.domain.Schedule;
import topetBack.topetBack.schedule.validation.ScheduleValidator;


@RestController
@RequiredArgsConstructor
@Slf4j

public class ScheduleController {
	
	private final ScheduleRepository scheduleRepository;
	private final ScheduleValidator vaildator;
	
	@InitBinder
	public void init(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(vaildator);
	}
	
	@GetMapping("/api/schedule")
    public String getCalendar() {
        log.info("get Calendar");
        return "get Calendar!";
    }
    //get post 
	
	 @PostMapping("/api/schedule/post")
	    public Object schedulePost(@ModelAttribute Schedule schedule , BindingResult bindingResult) {
	    	vaildator.validate(schedule, bindingResult);
	    	if(bindingResult.hasErrors()) {
	            return bindingResult.getFieldErrors();
	        }
	        log.info("/api/schedule/post 진입");
	        log.info("입력한 내용은 {}", schedule);
	        
	    	Schedule response = scheduleRepository.save(schedule);
	    	log.info("schedule db저장 " + response);
	        return "일정이 성공적으로 등록되었습니다.";
	    }
	 
	 @PostMapping(value =  "/api/schedule/postPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public String schedulePostPhoto(@RequestPart(value = "photo", required = true) MultipartFile photo) {
	        log.info("/api/schedule/postPhoto 진입");
	      
	         
	     // JSON 객체 생성
	        JsonObject jsonObject = new JsonObject();
	        // 이미지 파일이 저장될 경로 설정
	        String fileRoot  = "C:\\toPetTemp/image/";
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
	            jsonObject.addProperty("toPetTemp", "/image/" + savedFileName);
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
	          
	         
	        
	        // 여기서 schedule 객체를 활용하여 필요한 데이터베이스 저장 등의 로직을 수행할 수 있어야 합니다.
	        
	        return "사진 요청 옴";
	    }
	 
	 
	 
}

