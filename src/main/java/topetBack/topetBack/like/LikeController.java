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
import topetBack.topetBack.comment.application.CommentService;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.like.application.LikeServiceImpl;
import topetBack.topetBack.like.domain.LikeResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.SessionMember;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/like")
public class LikeController {
	private final LikeServiceImpl likeService;
    private final SessionManager sessionManager;
	
	@PostMapping("/{id}")
    public ResponseEntity<CommunityResponseDTO> likePost(@PathVariable("id") Long id, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		 SessionMember sessionMember = sessionManager.getSessionObject(request);
	     Member author = sessionMember.toMember();
        return likeService.likePost(id, author);
    }
	
	@GetMapping("/get")
	 public List<LikeResponseDTO> getPost(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		 SessionMember sessionMember = sessionManager.getSessionObject(request);
	     Member author = sessionMember.toMember();
       return likeService.findByAuthor(author);
   }
	
	@GetMapping("/detail/{id}")
	public boolean detailGetPost(@PathVariable("id") Long id, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		 SessionMember sessionMember = sessionManager.getSessionObject(request);
	     Member author = sessionMember.toMember();
	     boolean likedByCurrentUser = likeService.getDetailLike(id, author);
       return likedByCurrentUser;
   }
	
}
