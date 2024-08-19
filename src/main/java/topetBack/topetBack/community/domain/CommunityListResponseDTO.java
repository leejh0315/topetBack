package topetBack.topetBack.community.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.hashTag.domain.HashTagResponseDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityListResponseDTO {
	private Long id;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private String title;
	private String content;
	private List<HashTagResponseDTO> hashtag;
	private String thumbnail;
	private int commentCount;
	private long likeCount;
}
