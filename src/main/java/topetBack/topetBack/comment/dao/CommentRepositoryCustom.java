package topetBack.topetBack.comment.dao;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.community.domain.CommunityEntity;
import org.springframework.data.domain.Pageable;

@Repository
public interface CommentRepositoryCustom {

	Slice<CommentEntity> findByCommunityId(Long community, Pageable pageable);

	Slice<CommentEntity> findByAuthorId(Long authorId, Pageable pageable);

}
