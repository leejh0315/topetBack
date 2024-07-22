package topetBack.topetBack.comment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;


@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

	CommentEntity save(CommunityEntity communityEntity);
	
	
	@Transactional
    void deleteByCommunityId(Long communityId);
	
	

}
