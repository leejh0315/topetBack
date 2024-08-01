package topetBack.topetBack.comment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.community.domain.CommunitySummaryResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberResponseDTO;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {

    private Long id;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private boolean deleted;
    private Long parentId; // 부모 댓글 ID 추가
    private MemberSummaryResponseDTO author;
    private CommunitySummaryResponseDTO community;
    private List<CommentResponseDTO> children;
}
