package topetBack.topetBack.likes.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.member.domain.Member;

public interface LikeRepository extends JpaRepository<Likes, Long>{

	Optional<Likes> findByCommunityAndAuthor(CommunityEntity communityEntity, Member author);
	List<Likes> findByAuthor(Member meber);
	boolean existsByCommunityAndAuthor(CommunityEntity communityEntity, Member member);

}
