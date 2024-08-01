package topetBack.topetBack.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.like.domain.Like;
import topetBack.topetBack.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunitySummaryResponseDTO {
	private Long id;
	private String title;
}
