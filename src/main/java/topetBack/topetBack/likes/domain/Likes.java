package topetBack.topetBack.likes.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Getter
@Entity // Member 라는 객체와 DB 테이블을 매핑합니다. JPA가 관리합니다.
@NoArgsConstructor
@Table(name = "likes")
public class Likes {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    @JsonIgnore
    private CommunityEntity community;
	
	@Column(nullable = false)
    @Comment("좋아요 여부")
    private boolean isLike;

	public void isLike() {
        this.isLike = true;
    }
	
	@Builder
	public Likes(CommunityEntity community , Member author) {
		this.community = community;
		this.author = author;
	}

	public static Likes of(CommunityEntity community , Member author) {
		Likes likes = Likes.builder()
                .community(community)
                .author(author)
                .build();
        return likes;
    }
	
	public LikeResponseDTO toResponseDTO() {
        return LikeResponseDTO.builder()
                .id(this.id)
//                .author(this.author.toSummaryResponseDTO())
                .build();
                
                
    }
}
