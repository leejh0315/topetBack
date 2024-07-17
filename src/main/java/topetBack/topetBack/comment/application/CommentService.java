package topetBack.topetBack.comment.application;

import org.springframework.stereotype.Service;

import topetBack.topetBack.comment.domain.CommentRequestDTO;



@Service
public interface CommentService {
	public Long commentSave(String name , Long id , CommentRequestDTO dto);
}
