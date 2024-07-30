package topetBack.topetBack.like.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;

@Getter
@Entity // Member 라는 객체와 DB 테이블을 매핑합니다. JPA가 관리합니다.
@NoArgsConstructor
@Table(name = "likes")
public class Like {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;
	
	@ManyToOne
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
	public Like(CommunityEntity community , Member author) {
		this.community = community;
		this.author = author;
	}

	public static Like of(CommunityEntity community , Member author) {
		Like likes = Like.builder()
                .community(community)
                .author(author)
                .build();
        return likes;
    }
	
	public LikeResponseDTO toResponseDTO() {
      
        
        return LikeResponseDTO.builder()
                .id(this.id)
                .author(this.author)
                .build();
                
                
    }
}
