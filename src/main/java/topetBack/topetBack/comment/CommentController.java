package topetBack.topetBack.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;
	private final SessionManager sessionManager;

	 @PostMapping("/post/{id}")
	    public ResponseEntity<CommentResponseDTO> communityPost(@ModelAttribute CommentRequestDTO commentRequestDTO , @PathVariable("id")Long id, HttpServletRequest req) throws Exception  {
		 	SessionMember member = sessionManager.getSessionObject(req);
		 			
			commentRequestDTO.setAuthor(member.toMember());
			CommentResponseDTO commentEntity = commentService.insert(id, commentRequestDTO);
			
	        return ResponseEntity.ok(commentEntity);
		}
	 
	 @GetMapping("/get/{id}")
	 public ResponseEntity<List<CommentResponseDTO>> getCommentsByCommunityId(Model model, @PathVariable("id") Long id) {
		    List<CommentResponseDTO> comments = commentService.getCommentsByCommunityId(id);
		    return new ResponseEntity<>(comments, HttpStatus.OK);
		}
	 
	 @PostMapping("/update")
	    public ResponseEntity<CommentResponseDTO> updateComment(@ModelAttribute CommentRequestDTO commentUpdateRequestDTO) throws Exception {
	        CommentResponseDTO updatedComment = commentService.updateComment(commentUpdateRequestDTO);
	        return ResponseEntity.ok(updatedComment);
	    }
	 
	
	 //삭제
	 @PostMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
	        commentService.delete(id);
	        return ResponseEntity.noContent().build();
	    }
	 
	 
	 
	 


}
