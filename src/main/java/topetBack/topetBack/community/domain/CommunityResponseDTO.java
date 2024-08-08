package topetBack.topetBack.community.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.like.domain.Like;
import topetBack.topetBack.like.domain.LikeResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityResponseDTO {
	private Long id;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private String title;
	private MemberSummaryResponseDTO author;
	private String content;
	private String hashtag;
	private String category;
	private String animal;
    private List<FileResponseDTO> images;
    private List<CommentResponseDTO> comments;
    private List<LikeResponseDTO> likesList;
    private int commentCount;

}
