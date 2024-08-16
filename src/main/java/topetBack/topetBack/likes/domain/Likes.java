package topetBack.topetBack.likes.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunitySummaryResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;
import topetBack.topetBack.shorts.domain.ShortsEntity;
import topetBack.topetBack.shorts.domain.ShortsSummaryResponseDTO;

@Builder
@Getter
@Entity // Member 라는 객체와 DB 테이블을 매핑합니다. JPA가 관리합니다.
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Likes {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnore
    private Member author; 
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = true)
    @JsonIgnore
    private CommunityEntity community;  
	
	@Comment("쇼츠")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shorts_id", nullable = true)
    private ShortsEntity shorts;  
	
	public LikeResponseDTO toResponseDTO() {
        return LikeResponseDTO.builder()
                .id(this.id)
                .build();
    }
	
	public LikeCommunityDTO toCommunityDTO() {
		return LikeCommunityDTO.builder()
				.id(this.id)
				.community(this.community.toSummaryResponseDTO())  // 필요 시 DTO로 변환
				.author(this.author.toSummaryResponseDTO())
				.build();
	}
	
	public LikeShortsDTO toShortsDTO() {
		return LikeShortsDTO.builder()
				.id(this.id)
				.shorts(this.shorts.toSummaryResponseDTO())  // 필요 시 DTO로 변환
				.author(this.author.toSummaryResponseDTO())
				.build();
	}
}
