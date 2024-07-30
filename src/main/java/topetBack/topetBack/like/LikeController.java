package topetBack.topetBack.like;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import topetBack.topetBack.like.application.LikeService;
import topetBack.topetBack.like.domain.LikeResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.SessionMember;


@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
	private final LikeService likeService;
    private final SessionManager sessionManager;
	
	@PostMapping("/{id}")
    public ResponseEntity<CommunityResponseDTO> likePost(@PathVariable("id") Long id, HttpServletRequest req) throws JsonMappingException, JsonProcessingException {
		SessionMember member = sessionManager.getSessionObject(req);
        return likeService.likePost(id, member.toMember());
    }
	
	@GetMapping("/get")
	 public List<LikeResponseDTO> getPost(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		Member member = sessionManager.getSessionObject(request).toMember();
       return likeService.findByAuthor(member);
   }
	
	@GetMapping("/detail/{id}")
	public boolean detailGetPost(@PathVariable("id") Long id, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		Member member = sessionManager.getSessionObject(request).toMember();
	    boolean likedByCurrentUser = likeService.getDetailLike(id, member);
       return likedByCurrentUser;
   }
	
}
