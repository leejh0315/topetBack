package topetBack.topetBack.report.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityListResponseDTO;
import topetBack.topetBack.member.domain.Member;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CommentReportEntity {
	
	private Long id;
    private String reason;
    private Long commentId;
    private String commentText;
    private Long authorId;
    private String authorName;
}
