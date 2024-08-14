package topetBack.topetBack.comment.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.shorts.domain.ShortsEntity;

@Repository
public interface CommentRepositoryCustom {

	Slice<CommentEntity> findByCommunityId(Long community, Pageable pageable);
	Slice<CommentEntity> findByShortsId(Long shorts, Pageable pageable);
	
	Slice<CommentEntity> findByAuthorId(Long authorId, Pageable pageable);

}
