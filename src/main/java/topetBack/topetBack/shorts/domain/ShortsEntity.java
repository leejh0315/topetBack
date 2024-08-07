package topetBack.topetBack.shorts.domain;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.member.domain.Member;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shorts")
@ToString
@Entity
public class ShortsEntity {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @Comment("쇼츠 번호")
    private Long id;
    
	@ManyToOne
	private Member author;
	
	private String title;
	private String content;
	private String thumbnailPhotoSrc;
	private String videoSrc;
	
	
	public ShortsResponseDTO toResponseDTO() {
		return ShortsResponseDTO.builder()
				.id(this.id)
				.title(this.title)
				.content(this.content)
				.author(this.author.toResponseDTO())
				.thumbnailPhotoSrc(this.thumbnailPhotoSrc)
				.videoSrc(this.videoSrc)
				.build();
				
	}

	  	
}
