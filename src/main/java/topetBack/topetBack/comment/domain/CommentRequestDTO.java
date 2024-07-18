package topetBack.topetBack.comment.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDTO {
	
	private Long id;   
	private Member author;	    	    
	private CommunityEntity community;		
	private String content;

	public CommentEntity toCommentEntity() {
		
		return CommentEntity.builder()
				.author(this.author)
				.content(this.content)
				.community(this.community)
				.build();
	}

	
}
