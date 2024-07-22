package topetBack.topetBack.comment.application.copy;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.comment.domain.CommentResponseDTO;



@Service
public interface ComentService {
	public CommentResponseDTO insert(Long CommunityId , CommentRequestDTO commentRequestDTO) throws NotFoundException;


}
