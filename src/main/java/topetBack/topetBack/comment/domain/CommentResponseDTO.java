package topetBack.topetBack.comment.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Data
@Builder
public class CommentResponseDTO {
	
	 private Long id;
	 private LocalDateTime createdTime;
	 private LocalDateTime updatedTime;	    
	 private Member author;	    	    
	 private CommunityEntity community;		
	 private String content;
	 
	 
	
}
