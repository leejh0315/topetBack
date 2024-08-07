package topetBack.topetBack.shorts.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.member.domain.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortsRequestDTO {
	
	private Long id;
	private String title;
	private String content;
	private Member author;
	private String thumbnailPhotoSrc;
	
	private String videoSrc;
	
	private MultipartFile thumbnailPhoto;
	private MultipartFile video;
	
	

	public ShortsEntity toShortsEntity() {
		return ShortsEntity.builder()
				.id(this.id)
				.title(this.title)
				.author(this.author)
				.content(this.content)
				.thumbnailPhotoSrc(this.thumbnailPhotoSrc)
				.videoSrc(this.videoSrc)
				.build();
	}
}
