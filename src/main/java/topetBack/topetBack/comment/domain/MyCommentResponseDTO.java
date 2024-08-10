package topetBack.topetBack.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunitySummaryResponseDTO;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyCommentResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private CommunitySummaryResponseDTO community;
    private String content;
    private Long parentId; // 부모 댓글 ID 추가
}
