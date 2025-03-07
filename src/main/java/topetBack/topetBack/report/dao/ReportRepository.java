package topetBack.topetBack.report.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.report.domain.ReportEntitiy;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntitiy, Long>{

	Optional<ReportEntitiy> findByCommunityAndAuthor(CommunityEntity communityEntity, Member author);

	void deleteByCommunity(CommunityEntity community);

	void deleteByComment(CommentEntity comment);

}
