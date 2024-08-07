package topetBack.topetBack.report.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.like.domain.Like;
import topetBack.topetBack.member.domain.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
public class ReportEntitiy {
	
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
	 @Comment("게시판 번호")
	 private Long id;

	 @Comment("신고자")
	 @ManyToOne
	 private Member author;

	 @Column(nullable = false)
	 @Comment("신고내용")
	 private String reason;
	 
	 @ManyToOne
	 @JoinColumn(name = "community_id", nullable = false)
	 @JsonIgnore
	 private CommunityEntity community;
	 
	 @CreationTimestamp
	 @Comment("신고 시간")
	 private LocalDateTime createdTime;
	 
	 public static ReportEntitiy of(CommunityEntity community , Member author , String reason) {
		 ReportEntitiy report = ReportEntitiy.builder()
	                .community(community)
	                .author(author)
	                .reason(reason)
	                .build();
	        return report;
	    }
	 
}
