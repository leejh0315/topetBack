package topetBack.topetBack.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.file.domain.FileResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

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
	private String hashtag;
	private String thumbnail;
	private int commentCount;
	private long likeCount;
}
