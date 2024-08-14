package topetBack.topetBack.shorts;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.shorts.domain.ShortsRequestDTO;
import topetBack.topetBack.shorts.domain.ShortsResponseDTO;
import topetBack.topetBack.shorts.service.ShortsService;

@RestController
@RequestMapping("/shorts")
@RequiredArgsConstructor
public class ShortsController {

	private final ShortsService shortsService;
	private final SessionManager sessionManager;
		
	@PostMapping("/post")
	public ResponseEntity<ShortsResponseDTO> postShorts(@ModelAttribute ShortsRequestDTO shortsRequestDTO, HttpServletRequest req,
			@RequestParam(value="thumbnailPhoto", required=true) MultipartFile image,
			@RequestParam(value="video", required=false) MultipartFile video) throws IOException{
		
//		Member member = sessionManager.getSessionObject(req).toMember();
//		shortsRequestDTO.setAuthor(member);
		ShortsResponseDTO shortsResponseDTO =  shortsService.shortsSave(shortsRequestDTO);
		return ResponseEntity.ok(shortsResponseDTO);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ShortsResponseDTO>> getAllShorts(){
		List<ShortsResponseDTO> allShorts =  shortsService.getAll();
		return ResponseEntity.ok(allShorts);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<ShortsResponseDTO> getDetailById(@PathVariable("id")Long id){
		ShortsResponseDTO shortsResponseDTO = shortsService.getShortsDetail(id);
		return ResponseEntity.ok(shortsResponseDTO);
	}
	
	@GetMapping("/random")
	public ResponseEntity<Long> getRandomShorts(){
		Long shortsResponseDTO = shortsService.getRandomShorts();
		return ResponseEntity.ok(shortsResponseDTO);
	}
	
	
}
