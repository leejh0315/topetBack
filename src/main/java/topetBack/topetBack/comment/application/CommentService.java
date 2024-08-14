package topetBack.topetBack.comment.application;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.comment.domain.MyCommentResponseDTO;


@Service
public interface CommentService {
	CommentResponseDTO insert(Long CommunityId , CommentRequestDTO commentRequestDTO) throws NotFoundException, AccessDeniedException;
	List<CommentResponseDTO> getCommentsByCommunityId(Long communityId, int page, int size );
	List<MyCommentResponseDTO> getCommentsByAuthorId(Long authorId, int page, int size);
	CommentResponseDTO updateComment(CommentRequestDTO commentUpdateRequestDTO) throws NotFoundException;
	void delete(Long id);
}
