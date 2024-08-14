package topetBack.topetBack.comment.domain;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.shorts.domain.ShortsEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDTO {

    private Long id;
    private Member author;
    private CommunityEntity community;
    private ShortsEntity shorts;
    private String content;
    private Long parentId;

    public CommentEntity toCommentEntity() {
        CommentEntity.CommentEntityBuilder builder = CommentEntity.builder()
                .author(this.author)
                .content(this.content)
                .children(new ArrayList<>());

        return builder.build();
    }
}
