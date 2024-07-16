package topetBack.topetBack.community.domain;

import lombok.Builder;
import lombok.Data;
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommunityResponseDTO {
	private Long id;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private Member author;
	private String title;
	private String content;
	private String hashtag;
	private String category;
	private String animal;
    private List<FileResponseDTO> images;
    private List<CommentResponseDTO> comments;

}
