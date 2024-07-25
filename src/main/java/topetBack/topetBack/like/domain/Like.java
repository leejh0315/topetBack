package topetBack.topetBack.like.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Getter
@Entity // Member 라는 객체와 DB 테이블을 매핑합니다. JPA가 관리합니다.
@NoArgsConstructor
@Table(name = "likes")
public class Like {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "autohr_id", nullable = false)
    private Member author;
	
	@ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    private CommunityEntity community;
	
	public Like(CommunityEntity community , Member author) {
		this.community = community;
		this.author = author;
	}

	
}
