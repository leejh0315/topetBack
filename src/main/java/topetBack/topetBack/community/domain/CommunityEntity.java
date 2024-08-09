package topetBack.topetBack.community.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.file.domain.FileGroupEntity;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

	@Column(nullable = false)
    @Comment("제목")
    private String title;

	@Column(nullable = false)
    @Comment("내용")
    private String content;

	@Column(nullable = true)
    @Comment("해시태그")
    private String hashtag;

	@Column(nullable = false)
    @Comment("카테고리")
    private String category;

	@Column(nullable = true)
    @Comment("반려 동물")
    private String animal;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_group_id")
    private FileGroupEntity fileGroupEntity;

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Likes> likesList = new ArrayList<>();
    
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
                .hashtag(this.hashtag)
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


    
    public void updateCommunity(String title, String content , String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    

}
