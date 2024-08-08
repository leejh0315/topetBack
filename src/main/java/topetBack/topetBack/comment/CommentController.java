package topetBack.topetBack.comment;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.application.CommentService;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.config.SessionManager;
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
	 public ResponseEntity<List<CommentResponseDTO>> getCommentsByCommunityId(Model model,
																			  @PathVariable("id") Long id,
																			  @RequestParam(name = "page") int page,
																			  @RequestParam(name = "size") int size) {
		    List<CommentResponseDTO> comments = commentService.getCommentsByCommunityId(id, page, size);
		    return new ResponseEntity<>(comments, HttpStatus.OK);
	 }

	 @GetMapping("/myComment")
	 public ResponseEntity<List<CommentResponseDTO>> getCommentsByAuthorId(HttpServletRequest request,
																		   @RequestParam(name = "page") int page,
																		   @RequestParam(name = "size") int size) throws JsonProcessingException {
		 SessionMember member = sessionManager.getSessionObject(request);
		 List<CommentResponseDTO> comments = commentService.getCommentsByAuthorId(member.getId(), page, size);

		 return ResponseEntity.ok(comments);
	 }

	@GetMapping("/author/{id}")
	public ResponseEntity<List<CommentResponseDTO>> getCommentsByAuthorId(@PathVariable("id") Long id,
																		  @RequestParam(name = "page") int page,
																		  @RequestParam(name = "size") int size)  {

		List<CommentResponseDTO> comments = commentService.getCommentsByAuthorId(id, page, size);
		return ResponseEntity.ok(comments);
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
