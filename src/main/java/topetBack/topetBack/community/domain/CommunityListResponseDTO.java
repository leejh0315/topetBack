package topetBack.topetBack.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunitySummaryResponseDTO {
	private Long id;
	private String title;
	private String content;
	private String hashtag;
	private List<FileResponseDTO> images;
	private int commentCount;
	private long likeCount;
}
