package topetBack.topetBack.comment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberResponseDTO;

@Data
@Builder
public class CommentResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private MemberResponseDTO author;
    private CommunityResponseDTO community;
    private String content;
    private List<CommentResponseDTO> children;
    private boolean deleted;
    private Long parentId; // 부모 댓글 ID 추가
}
