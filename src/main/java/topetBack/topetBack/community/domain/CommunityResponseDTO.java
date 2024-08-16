package topetBack.topetBack.community.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.hashTag.domain.HashTagResponseDTO;
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
	private List<HashTagResponseDTO> hashtag;
	private String category;
	private String animal;
    private List<FileResponseDTO> images;
    private int commentCount;
	private long likeCount;

}
