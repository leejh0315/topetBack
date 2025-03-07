package topetBack.topetBack.comment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private MemberSummaryResponseDTO author;
    private String content;
    private List<CommentResponseDTO> children = new ArrayList<>();
    private Long parentId; // 부모 댓글 ID 추가
}
