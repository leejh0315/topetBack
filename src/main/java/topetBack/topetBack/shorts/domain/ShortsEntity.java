package topetBack.topetBack.shorts.domain;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "common_seq", sequenceName = "INTEREST_SEQ", allocationSize = 1)
public class ShortsEntity {
    
	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_seq")
    @Comment("쇼츠 번호")
    private Long id;
    
	@ManyToOne
	private Member author;
	
	private String title;
	private String content;
	private String thumbnailPhotoSrc;
	private String videoSrc;
	
	//좋아요 개수
    @Formula("(SELECT count(1) FROM likes l WHERE l.community_id = id)")
    private int likeCount;
	
	
	public ShortsResponseDTO toResponseDTO() {
		return ShortsResponseDTO.builder()
				.id(this.id)
				.title(this.title)
				.content(this.content)
				.author(this.author.toResponseDTO())
				.thumbnailPhotoSrc(this.thumbnailPhotoSrc)
				.videoSrc(this.videoSrc)
				.likeCount(this.likeCount)
				.build();
				
	}

	public ShortsSummaryResponseDTO toSummaryResponseDTO() {
	       return ShortsSummaryResponseDTO.builder()
	               .id(this.id)
	               .title(this.title)
	               .build(); // 필요한 필드만 포함
	   }
	  	
}
