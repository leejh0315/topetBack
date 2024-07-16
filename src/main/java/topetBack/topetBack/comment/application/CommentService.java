package topetBack.topetBack.comment.application;

import topetBack.topetBack.comment.domain.CommentRequestDTO;


public interface CommentService {
	public Long commentSave(String name , Long id , CommentRequestDTO dto);
}
