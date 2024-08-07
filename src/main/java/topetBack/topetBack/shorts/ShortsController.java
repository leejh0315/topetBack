package topetBack.topetBack.shorts;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
		
		Member member = sessionManager.getSessionObject(req).toMember();
		shortsRequestDTO.setAuthor(member);
		ShortsResponseDTO shortsResponseDTO =  shortsService.shortsSave(shortsRequestDTO);
		return ResponseEntity.ok(shortsResponseDTO);
	}
	
}
