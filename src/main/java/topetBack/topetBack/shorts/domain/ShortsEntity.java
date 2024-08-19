package topetBack.topetBack.shorts.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.hashTag.domain.TagMapping;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.member.domain.Member;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shorts")
@ToString
@Entity
//@SequenceGenerator(name = "common_seq", sequenceName = "INTEREST_SEQ", allocationSize = 1)
public class ShortsEntity {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_seq")
    @Comment("쇼츠 번호")
    private Long id;
    
	@ManyToOne(cascade=CascadeType.ALL )
	@OnDelete(action = OnDeleteAction.CASCADE) 
	private Member author;
	
	private String content;
	private String thumbnailPhotoSrc;
	private String videoSrc;
	
	//댓글 개수
//    @Formula("(SELECT count(1) FROM comments c WHERE c.shorts_id = id)")
//    private int commentCount;

    //좋아요 개수
//    @Formula("(SELECT count(1) FROM likes l WHERE l.shorts_id = id)")
//    private int likeCount;	
//    
    
//    @OneToMany(mappedBy = "shorts",cascade = CascadeType.REMOVE)
//    private List<CommentEntity> comment;
//    
//    
//    @OneToMany(mappedBy = "shorts",cascade = CascadeType.REMOVE)
//    private List<Likes> likes;
	
    
	public ShortsResponseDTO toResponseDTO() {
		return ShortsResponseDTO.builder()
				.id(this.id)
//				.title(this.title)
				.content(this.content)
				.author(this.author.toResponseDTO())
				.thumbnailPhotoSrc(this.thumbnailPhotoSrc)
				.videoSrc(this.videoSrc)
//				.likeCount(this.likeCount)
//                .commentCount(this.commentCount)

				.build();
				
	}

	public ShortsSummaryResponseDTO toSummaryResponseDTO() {
	       return ShortsSummaryResponseDTO.builder()
	               .id(this.id)
	               .content(this.content)
	               .thumbnail(this.thumbnailPhotoSrc)
//	                .commentCount(this.commentCount)
//	                .likeCount(this.likeCount)
	               .build(); // 필요한 필드만 포함
	   }
	  	
}
