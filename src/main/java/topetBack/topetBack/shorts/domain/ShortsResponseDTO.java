package topetBack.topetBack.shorts.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberResponseDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor	
public class ShortsResponseDTO {
	private Long id;
	private String title;
	private String content;
	private MemberResponseDTO author;
	private String thumbnailPhotoSrc;
	private String videoSrc;
    private int likeCount;
}
