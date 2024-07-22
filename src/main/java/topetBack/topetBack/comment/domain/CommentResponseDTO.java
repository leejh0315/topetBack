package topetBack.topetBack.comment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Data
@Builder
public class CommentResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Member author;
    private CommunityEntity community;
    private String content;
    private List<CommentResponseDTO> children = new ArrayList<>();
    private boolean deleted;
    private Long parentId; // 부모 댓글 ID 추가
}
