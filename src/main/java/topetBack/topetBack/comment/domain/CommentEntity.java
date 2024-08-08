package topetBack.topetBack.comment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.member.domain.Member;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    @Comment("댓글 번호")
    private Long id;

    @CreationTimestamp
    @Comment("업로드 시간")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Comment("업데이트 시간")
    private LocalDateTime updatedTime;

    @Column(nullable = false)
    @Comment("삭제 여부")
    private boolean deleted;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @JsonManagedReference
    private List<CommentEntity> children = new ArrayList<>();
    
    public void delete() {
        this.deleted = true;
    }

    private boolean hasChildren() {
        return getChildren().size() != 0;
    }

    public CommentResponseDTO toResponseDTO() {
    	List<CommentResponseDTO> childrenDTOs = new ArrayList<>();
        for (CommentEntity child : children) {
            childrenDTOs.add(CommentResponseDTO.builder()
                    .id(child.id)
                    .createdTime(child.createdTime)
                    .updatedTime(child.updatedTime)
                    .author(child.author.toSummaryResponseDTO())
                    .community(child.community.toSummaryResponseDTO()) // 자식 댓글은 community를 가지지 않도록 설정
                    .content(child.content)
                    .children(new ArrayList<>())
                    .parentId(this.id)
                    .build());
        }


        return CommentResponseDTO.builder()
                .id(this.id)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .author(this.author.toSummaryResponseDTO())
                .community(this.community.toSummaryResponseDTO())
                .content(this.content)
                .children(childrenDTOs)
                .parentId(this.parent != null ? this.parent.getId() : null)
                .build();
    }

    public void updateParent(Object parentComment) {
        this.parent = (CommentEntity) parentComment;
    }

    public void updateCommunity(CommunityEntity communityEntity) {
        this.community = communityEntity;
    }

    public void updateAuthor(Member member) {
        this.author = member;
    }

    public CommentEntity(String content) {
        this.content = content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    
    
    
}
