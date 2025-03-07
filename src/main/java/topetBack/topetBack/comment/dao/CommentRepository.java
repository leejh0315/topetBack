package topetBack.topetBack.comment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.shorts.domain.ShortsEntity;


@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, CommentRepositoryCustom {

	CommentEntity save(CommunityEntity communityEntity);

	@Transactional
    void deleteByCommunityId(Long communityId);

	List<CommentEntity> findByCommunityId(Long post_id);
	

	
	
	

}
