package topetBack.topetBack.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> , JpaSpecificationExecutor<CommunityEntity>{


	List<CommunityEntity> findByAnimalAndCategory(String animal, String category);

	List<CommunityEntity> findByCategory(String category);

	@Override
	Optional<CommunityEntity> findById(Long communityId);
	
	List<CommunityEntity> findByAuthorId(Long id);//사용자에 맞는 게시글 가져오기



}
