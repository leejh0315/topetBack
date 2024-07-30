package topetBack.topetBack.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> , JpaSpecificationExecutor<CommunityEntity>{


	List<CommunityEntity> findByAnimalAndCategory(String animal, String category);
    Slice<CommunityEntity> findByAnimalAndCategoryOrderByCreatedTimeDesc(String animal, String category, Pageable pageable);

	List<CommunityEntity> findByCategory(String category);

	@Override
	Optional<CommunityEntity> findById(Long communityId);
	
	List<CommunityEntity> findByAuthorId(Long id);//사용자에 맞는 게시글 가져오기
	
    @Query("SELECT c FROM CommunityEntity c LEFT JOIN c.likesList l WHERE c.animal = :animal AND c.category = :category GROUP BY c ORDER BY COUNT(l) DESC")
	List<CommunityEntity> findAllOrderByLikesCountDesc(@Param("animal") String animal, @Param("category") String category);
    
    @Query("SELECT c FROM CommunityEntity c LEFT JOIN c.likesList l WHERE c.animal = :animal GROUP BY c ORDER BY COUNT(l) DESC")
    List<CommunityEntity> findAnimalOrderByLikesCountDesc(@Param("animal") String animal);


}
