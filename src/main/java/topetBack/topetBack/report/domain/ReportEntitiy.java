package topetBack.topetBack.report.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReportEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("게시판 번호")
    private Long id;

    @Comment("신고자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Member author;

    @Column(nullable = false)
    @Comment("신고내용")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "community_id", nullable = true)
    @JsonIgnore
    private CommunityEntity community;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "comment_id", nullable = true)
    @JsonIgnore
    private CommentEntity comment;

    @CreationTimestamp
    @Comment("신고 시간")
    private LocalDateTime createdTime;

    public CommunityReportEntity toCommunityReportDTO() {
        return CommunityReportEntity.builder()
                .id(this.id)
                .reason(this.reason)
                .communityId(this.community.getId())
                .communityTitle(this.community.getTitle())
                .authorId(this.author.getId())
                .authorName(this.author.getName())
                .build();
    }

    public CommentReportEntity toCommentReportDTO() {
        return CommentReportEntity.builder()
                .id(this.id)
                .reason(this.reason)
                .commentId(this.comment.getId())
                .commentText(this.comment.getContent())
                .authorId(this.author.getId())
                .authorName(this.author.getName())
                .build();
    }
}
