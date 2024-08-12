package topetBack.topetBack.block.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockResponseDTO {
	private Long id;
	private Member blocker;
	private Member blocked;
}
