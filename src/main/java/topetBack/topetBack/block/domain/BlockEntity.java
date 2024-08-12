package topetBack.topetBack.block.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "blocker_id")
    private Member blocker; // 차단을 수행한 사용자
	
	@ManyToOne
    @JoinColumn(name = "blocked_id")
    private Member blocked; // 차단된 사용자
	
	public BlockResponseDTO toBlockResponseDTO() {
			return BlockResponseDTO.builder()
					.id(this.id)
					.blocker(this.blocker)
					.blocked(this.blocked)
					.build();
	}
}
