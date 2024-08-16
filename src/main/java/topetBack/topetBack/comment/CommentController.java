package topetBack.topetBack.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.application.CommentService;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.comment.domain.MyCommentResponseDTO;
import topetBack.topetBack.config.SessionManager;


@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;
	private final SessionManager sessionManager;

	 @PostMapping("/post/{id}")
	    public ResponseEntity<CommentResponseDTO> communityPost(@ModelAttribute CommentRequestDTO commentRequestDTO , @PathVariable("id")Long id, HttpServletRequest req) throws Exception  {
//		 	SessionMember member = sessionManager.getSessionObject(req);
		 			
//			commentRequestDTO.setAuthor(member.toMember());
		 	System.out.println("commentRequestDTO : "+ commentRequestDTO);
		 
			CommentResponseDTO commentEntity = commentService.insert(id, commentRequestDTO);
			
	        return ResponseEntity.ok(commentEntity);
	 }
	 
	 @GetMapping("/get/{id}/{type}")
	 public ResponseEntity<List<CommentResponseDTO>> getCommentsByCommunityId(Model model,
																			  @PathVariable("id") Long id,
																			  @PathVariable("type") String type,
																			  @RequestParam(name = "page") int page,
																			  @RequestParam(name = "size") int size,
																			  HttpServletRequest req) throws JsonMappingException {
		 	System.out.println(type);
		    List<CommentResponseDTO> comments = commentService.getCommentsByCommunityId(id, page, size, type);
		    return new ResponseEntity<>(comments, HttpStatus.OK);
	 }

	 @GetMapping("/myComment")
	 public ResponseEntity<List<MyCommentResponseDTO>> getCommentsByAuthorId(HttpServletRequest request,
																		   @RequestParam(name = "page") int page,
																		   @RequestParam(name = "size") int size) throws JsonProcessingException {
		 Long memberId = sessionManager.getSessionObject(request);
		 List<MyCommentResponseDTO> comments = commentService.getCommentsByAuthorId(memberId, page, size);

		 return ResponseEntity.ok(comments);
	 }

	@GetMapping("/author/{id}")
	public ResponseEntity<List<MyCommentResponseDTO>> getCommentsByAuthorId(@PathVariable("id") Long id,
																			@RequestParam(name = "page") int page,
																			@RequestParam(name = "size") int size)  {

		List<MyCommentResponseDTO> comments = commentService.getCommentsByAuthorId(id, page, size);
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
