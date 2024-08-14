package topetBack.topetBack.schedule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.domain.PetEntity;
import topetBack.topetBack.schedule.application.ScheduleService;
import topetBack.topetBack.schedule.domain.ScheduleEntity;
import topetBack.topetBack.schedule.domain.ScheduleRequestDTO;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;
import topetBack.topetBack.schedule.validation.ScheduleValidator;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleValidator scheduleValidator;
    private final SessionManager sessionManager;

//    @InitBinder
//    public void init(WebDataBinder webDataBinder) {
//        webDataBinder.addValidators(scheduleValidator);
//    }
//
//    @GetMapping("/schedule")
//    public ResponseEntity<ScheduleEntity> getCalendar() {
//        log.info("get Calendar");
//		ScheduleEntity scheduleEntity = new ScheduleEntity(); // select from DB
//        return ResponseEntity.ok(scheduleEntity);
//    }

    @PostMapping("/post")
    public ResponseEntity<Object> schedulePost(
    							@ModelAttribute ScheduleRequestDTO scheduleRequestDTO, 
    							@RequestParam(value="photo", required=false) MultipartFile image,
    							@RequestParam(value="animal", required=true) PetEntity pet,
//    							 @PathVariable("id")Long id,
    							BindingResult bindingResult, HttpServletRequest req) throws IOException{
    	
    	scheduleRequestDTO.setAnimal(pet);   	
    	System.out.println("schedulePost요청왔음");
    	System.out.println("scheduleRequestDTO :  "+ scheduleRequestDTO);
        scheduleValidator.validate(scheduleRequestDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(scheduleRequestDTO);
        }
        
        
        log.info("/api/schedule/post 진입");
        log.info("입력한 내용은 {}", scheduleRequestDTO);
        
        ScheduleResponseDTO scheduleResponseDTO = scheduleService.createSchedule(scheduleRequestDTO , image);
        
//        return "success"; 
		return ResponseEntity.ok(scheduleResponseDTO);
    }

    @GetMapping("/{id}")
    public List<ScheduleResponseDTO> getTodaySchedules(HttpServletRequest req, @PathVariable("id")Long id){
    	System.out.println("오늘의 일정");
    	List<ScheduleResponseDTO> scheduleResponseDTO = scheduleService.findTodaySchedules(id);
    	System.out.println("scheduleResponseDTO : " + scheduleResponseDTO);
    	return scheduleResponseDTO;
    }
    


    @GetMapping("/get/{id}")	
    public List<ScheduleResponseDTO> getPetSchedule(@PathVariable("id")Long id){
    	return scheduleService.findByAnimalId(id); 
    }
    
    @PatchMapping("/status/{id}")
    public String updateStatusById(@PathVariable("id")Long id,  @RequestBody ScheduleRequestDTO scheduleRequestDTO){
    	System.out.println("scheduleEntity :"+scheduleRequestDTO);
    	System.out.println("-----------------------------------");
    	System.out.println("업데이트");
    	System.out.println("-----------------------------------");
    	
    	scheduleService.updateSchedule(scheduleRequestDTO);
    	
    	return "";
    }
    
    @PostMapping("/update/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateScheduleById(@PathVariable("id")Long id, HttpServletRequest req,
    															@ModelAttribute ScheduleRequestDTO scheduleRequestDTO, 
    															@RequestParam(value="photo", required=false) MultipartFile image,
    															@RequestParam(value="author", required=false)Long authorId) throws IOException{

    	System.out.println(scheduleRequestDTO);
//
//    	Member sessionMember = sessionManager.getSessionObject(req).toMember();
//    	
//    	scheduleRequestDTO.setUpdateAuthor(sessionMember);
//    	
    	ScheduleResponseDTO scheduleResponseDTO = scheduleService.createSchedule(scheduleRequestDTO, image);

    	return ResponseEntity.ok(scheduleResponseDTO);
    }
    
    @PostMapping(value = "/api/schedule/postPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String schedulePostPhoto(@RequestPart(value = "photo", required = true) MultipartFile photo) {
        log.info("/api/schedule/postPhoto 진입");

        // JSON 객체 생성
        JsonObject jsonObject = new JsonObject();
        // 이미지 파일이 저장될 경로 설정
        String fileRoot = "C:\\toPetTemp/image/";
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

