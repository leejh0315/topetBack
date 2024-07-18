package topetBack.topetBack.comment;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import topetBack.topetBack.comment.application.CommentService;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.member.domain.Member;


@RestController
@RequiredArgsConstructor
@Controller
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping("/commynity/{id}/comment")
    public String writeComment(@PathVariable Long id, CommentRequestDTO commentRequestDTO) {
		Member Username = commentRequestDTO.getAuthor();
		String name = Username.toString();
        commentService.commentSave(name , id , commentRequestDTO);

        return "성공";
    }
	
}
