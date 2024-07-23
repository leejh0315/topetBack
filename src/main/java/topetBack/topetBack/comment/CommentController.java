package topetBack.topetBack.comment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.application.CommentService;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.SessionMember;


@RestController
@RequiredArgsConstructor
@Controller
public class CommentController {

	private final CommentService commentService;
	private final SessionManager sessionManager;

	 @PostMapping("/{id}/comentPost")
	    public ResponseEntity<CommentResponseDTO> communityPost(@ModelAttribute CommentRequestDTO commentRequestDTO , @PathVariable("id")Long id, HttpServletRequest req) throws Exception  {
		 	SessionMember member = sessionManager.getSessionObject(req);
		 	
//	    	HttpSession session = req.getSession(false);
//			Member member = (Member) session.getAttribute(SessionVar.LOGIN_MEMBER);
			System.out.println("DTO : " + commentRequestDTO);
//			System.out.println("member :" + member);

			commentRequestDTO.setAuthor(member.toMember());


			System.out.println("commentPost 요청 등록됨");
			System.out.println(commentRequestDTO);

			CommentResponseDTO commentEntity = commentService.insert(id, commentRequestDTO);
			
	        return ResponseEntity.ok(commentEntity);
		}
	 
	 @GetMapping("/comment/{id}")
	    public List<CommentResponseDTO> getCommentsByCommunityId(Model model ,@PathVariable("id") Long id) {
		 	System.out.println("댓글 테스트" +  commentService.getCommentsByCommunityId(id));
	        return commentService.getCommentsByCommunityId(id);
	    }
	 
	 @PostMapping("/commentUpdate")
	    public ResponseEntity<CommentResponseDTO> updateComment(@ModelAttribute CommentRequestDTO commentUpdateRequestDTO) throws Exception {
	        CommentResponseDTO updatedComment = commentService.updateComment(commentUpdateRequestDTO);
	        return ResponseEntity.ok(updatedComment);
	    }
	 
	
	 @PostMapping("/comment/delete/{id}")
	    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
	        commentService.delete(id);
	        return ResponseEntity.noContent().build();
	    }
	 
	 
	 
	 


}
