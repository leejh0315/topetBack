package topetBack.topetBack.community.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.hashTag.domain.TagMapping;
import topetBack.topetBack.likes.domain.Likes;
import topetBack.topetBack.member.domain.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "community")
@ToString
@Entity
public class CommunityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @Comment("게시판 번호")
    private Long id;

	@CreationTimestamp
    @Comment("업로드 시간")
    private LocalDateTime createdTime;

	@UpdateTimestamp
    @Comment("업데이트 시간")
    private LocalDateTime updatedTime;

    @Comment("작성자")
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE) 
    private Member author;

	@Column(nullable = false)
    @Comment("제목")
    private String title;

	@Column(nullable = false)
    @Comment("내용")
    private String content;

//	@Column(nullable = true)
//    @Comment("해시태그")
//    private List<String> hashtag;

	@Column(nullable = false)
    @Comment("카테고리")
    private String category;
	
	@Column(nullable = true)
    @Comment("반려 동물")
    private String animal;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_group_id")
    private FileGroupEntity fileGroupEntity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_mapping_id")
    private TagMapping tagMappings;
    
    
    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Likes> likesList = new HashSet<>();
    
    @OneToMany(mappedBy = "community",cascade = CascadeType.REMOVE)
    private List<CommentEntity> comment;
    
    
    @OneToMany(mappedBy = "community",cascade = CascadeType.REMOVE)
    private List<Likes> likes;
	
    
    //댓글 개수
    @Formula("(SELECT count(1) FROM comments r WHERE r.community_id = id)")
    private int commentCount;

    //좋아요 개수
    @Formula("(SELECT count(1) FROM likes l WHERE l.community_id = id)")
    private int likeCount;
    
    public CommunityResponseDTO toResponseDTO() {

        return CommunityResponseDTO.builder()
                .id(this.id)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .author(this.author.toSummaryResponseDTO())
                .title(this.title)
                .content(this.content)
                .hashtag(this.tagMappings.getHashTagResponseDTOList())
                .category(this.category)
                .animal(this.animal)
                .images(this.fileGroupEntity.getFileResponseDTOList())
                
                .commentCount(this.commentCount)
                .likeCount(this.likeCount)
                .build();
    }

    public CommunitySummaryResponseDTO toSummaryResponseDTO() {
        return CommunitySummaryResponseDTO.builder()
                .id(this.id)
                .title(this.title)
                .build();
    }	
    
    public CommunityListResponseDTO toListResponseDTO() {
        return CommunityListResponseDTO.builder()
                .id(this.id)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .title(this.title)
                .content(this.content)
                .hashtag(
                		Optional.ofNullable(this.tagMappings)  // tagMappings가 null일 수 있음을 고려
                        .map(TagMapping::getHashTagResponseDTOList) // tagMappings가 null이 아니면 getHashTagResponseDTOList 호출
                        .filter(list -> list != null && !list.isEmpty()) // 리스트가 null이 아니고 비어 있지 않은지 확인
                        .orElse(null)  )
                .thumbnail(
                        Optional.ofNullable(this.fileGroupEntity.getFileResponseDTOList())
                                .flatMap(list -> list.stream().findFirst())
                                .map(FileResponseDTO::getPath)
                                .orElse(null)
                )
                .commentCount(this.commentCount)
                .likeCount(this.likeCount)
                .build();
    }


    
    public void updateCommunity(String title, String content //, List<String> hashtag
    		) {
        this.title = title;
        this.content = content;
//        this.hashtag = hashtag;
    }
    
    public CommunityEntity toDTO() {
        return CommunityEntity.builder()
                .id(this.id)
                .title(this.title)
                .build(); // 필요한 필드만 포함
    }
    

}
