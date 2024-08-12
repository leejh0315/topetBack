package topetBack.topetBack.report.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityListResponseDTO;
import topetBack.topetBack.member.domain.Member;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityReportEntity {
	private Long id;
	private Member author;
	private String reason;
	private CommunityEntity community;
}
