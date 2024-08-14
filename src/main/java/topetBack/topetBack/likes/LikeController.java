package topetBack.topetBack.likes;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.likes.application.LikeService;
import topetBack.topetBack.likes.domain.LikeResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberRequestDTO;


@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
	private final LikeService likeService;
    private final SessionManager sessionManager;
	
	@PostMapping("/{id}")
    public	 ResponseEntity<CommunityResponseDTO> likePost(@PathVariable("id") Long id, HttpServletRequest req) throws JsonMappingException, JsonProcessingException {
		Long memberId = sessionManager.getSessionObject(req);
        return likeService.likePost(id, memberId);
    }
	
	@GetMapping("/get")
	 public List<LikeResponseDTO> getPost(HttpServletRequest request, MemberRequestDTO memberRequestDTO) throws JsonMappingException, JsonProcessingException {
//		Long memberId = sessionManager.getSessionObject(request);
       return likeService.findByAuthor(memberRequestDTO.toMember());
   }
	
	@GetMapping("/detail/{id}")
	public boolean detailGetPost(@PathVariable("id") Long id, HttpServletRequest request, MemberRequestDTO memberRequestDTO) throws JsonMappingException, JsonProcessingException {
//		Member member = sessionManager.getSessionObject(request).toMember();
	    boolean likedByCurrentUser = likeService.getDetailLike(id, memberRequestDTO.toMember());
       return likedByCurrentUser;
   }
	
}
