package topetBack.topetBack.comment.dao;

import java.util.List;

import org.springframework.data.domain.Slice;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.community.domain.CommunityEntity;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {

	List<CommentResponseDTO> findByCommunityId(Long community);

	Slice<CommentResponseDTO> findByAuthorId(Long authorId, Pageable pageable);

	void updateComment(CommentEntity comment);





//	List<CommentResponseDTO> findByCommunityId(Long id);


}
