package topetBack.topetBack.shorts;

import java.io.IOException;
import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.shorts.domain.ShortsEntity;
import topetBack.topetBack.shorts.domain.ShortsRequestDTO;
import topetBack.topetBack.shorts.domain.ShortsResponseDTO;
import topetBack.topetBack.shorts.domain.ShortsSummaryResponseDTO;
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
		ShortsResponseDTO shortsResponseDTO =  shortsService.shortsSave(shortsRequestDTO);
		return ResponseEntity.ok(shortsResponseDTO);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ShortsResponseDTO>> getAllShorts(
																@RequestParam(name = "page") int page,
																@RequestParam(name = "size") int size
																)
	{
		System.out.println("asd");
		List<ShortsResponseDTO> allShorts =  shortsService.getAll(page,  size);
//		List<ShortsResponseDTO> allShorts = shortsService.getAll();
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
	
	@GetMapping("/myshorts/{id}")
	public ResponseEntity<List<ShortsResponseDTO>> myShorts(@PathVariable("id") Long authorId){
		List<ShortsResponseDTO> myShorts = shortsService.getMyShorts(authorId);
		return ResponseEntity.ok(myShorts);
	}
	
	@GetMapping("/fiveShorts")
	public ResponseEntity<List<ShortsSummaryResponseDTO>> fiveShorts(){
		List<ShortsSummaryResponseDTO> list = shortsService.getFive();
		System.out.println(list);
		return ResponseEntity.ok(list);
	}
	
	  @GetMapping("/likedShorts/{id}")
	    public ResponseEntity<List<ShortsSummaryResponseDTO>> getLikedShortsByAuthorId(@QuerydslPredicate(root = ShortsEntity.class) Predicate predicate,
													@PathVariable("id") Long id,
													@RequestParam(name = "page") int page,
													@RequestParam(name = "size") int size){
												    	
	    	List<ShortsSummaryResponseDTO> shortsList = shortsService.getLikedShortsByAuthorId(id, page, size);
	    	return new ResponseEntity<>(shortsList, HttpStatus.OK);
	    }
	    
	
}
