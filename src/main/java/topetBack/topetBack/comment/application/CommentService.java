package topetBack.topetBack.comment.application;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;



@Service
public interface CommentService {
	CommentResponseDTO insert(Long CommunityId , CommentRequestDTO commentRequestDTO) throws NotFoundException;
	List<CommentResponseDTO> getCommentsByCommunityId(Long communityId);
	List<CommentResponseDTO> getCommentsByAuthorId(Long authorId, int page, int size);
	CommentResponseDTO updateComment(CommentRequestDTO commentUpdateRequestDTO) throws NotFoundException;
	void delete(Long id);
}
