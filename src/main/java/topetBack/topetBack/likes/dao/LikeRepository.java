package topetBack.topetBack.like.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.like.domain.Like;
import topetBack.topetBack.member.domain.Member;

public interface LikeRepository extends JpaRepository<Like, Long>{

	Optional<Like> findByCommunityAndAuthor(CommunityEntity communityEntity,Member author);
	List<Like> findByAuthor(Member meber);
	boolean existsByCommunityAndAuthor(CommunityEntity communityEntity, Member member);

}
