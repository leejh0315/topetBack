package topetBack.topetBack.comment.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.Member;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class CommentEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @Comment("댓글 번호")
    private Long id;

	@CreationTimestamp
    @Comment("업로드 시간")
    private LocalDateTime createdTime;

	@UpdateTimestamp
    @Comment("업데이트 시간")
    private LocalDateTime updatedTime;

    @Comment("작성자")
    @ManyToOne
    private Member author;
    
    @Comment("게시판")
    @ManyToOne
    private CommunityEntity community;

	@Column(nullable = false)
    @Lob
    @Comment("내용")
    private String content;

    public CommentResponseDTO toResponseDTO() {
    	
	    	
        
        return CommentResponseDTO.builder()
                .id(this.id)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .author(this.author)
                .community(this.community)
                .content(this.content)
                .build();
    }
    
    
	
}
