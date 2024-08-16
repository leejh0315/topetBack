package topetBack.topetBack.likes.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunitySummaryResponseDTO;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;
import topetBack.topetBack.shorts.domain.ShortsEntity;
import topetBack.topetBack.shorts.domain.ShortsSummaryResponseDTO;

public interface LikeRepository extends JpaRepository<Likes, Long>{

    Optional<Likes> findByCommunityAndAuthor(CommunityEntity community, Member member);

    List<Likes> findByAuthor(Member member);

    boolean existsByCommunityAndAuthor(CommunityEntity communityEntity, Member member);

    Optional<Likes> findByShortsAndAuthor(ShortsEntity shorts, Member member);

    boolean existsByShortsAndAuthor(ShortsEntity shorts, Member member);

    @Modifying
    @Query("DELETE FROM Likes l WHERE l.community.id = :communityId AND l.author.id = :memberId")
    void removeLikeFromCommunity(@Param("communityId") Long communityId, @Param("memberId") Long memberId);

    @Modifying
    @Query("DELETE FROM Likes l WHERE l.shorts.id = :shortsId AND l.author.id = :memberId")
    void removeLikeFromShorts(@Param("shortsId") Long shortsId, @Param("memberId") Long memberId);

    @Modifying
    @Query("INSERT INTO Likes (community, author) VALUES (:community, :author)")
    void addLikeToCommunity(@Param("community") CommunityEntity community, @Param("author") Member author);

    @Modifying
    @Query("INSERT INTO Likes (shorts, author) VALUES (:shorts, :author)")
    void addLikeToShorts(@Param("shorts") ShortsEntity shorts, @Param("author") Member author);
}
