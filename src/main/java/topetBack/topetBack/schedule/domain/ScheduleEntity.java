package topetBack.topetBack.schedule.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.domain.PetEntity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedule")
@ToString
@Entity
public class ScheduleEntity {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("스케쥴 번호")
    private Long id;
    
    @Comment("시작 시간")
    @Column(nullable = false)
    private LocalDateTime startDate;
    
    @Comment("종료 시간")
    @Column(nullable = false)
    private LocalDateTime endDate;
    
    @Column(nullable = false)
    @Comment("제목")
    private String title;
    
    @Column(nullable = false)
    @Lob
    @Comment("내용")
    private String content;
    
    @Column(nullable = false)
    @Comment("완료여부")
    private Boolean isComplete;
    
    @Column(nullable = false)
    @Comment("색깔")
    private String color;
    
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @Comment("작성자")
    private Member author;
    
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "author_id", nullable = false)
//    @Comment("작성자")
//    private Member author;
    
    @ManyToOne//(fetch = FetchType.LAZY)//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "animal_id")
    @Comment("동물")
    private PetEntity animal;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "update_author_id")
    @Comment("수정자")
    private Member updateAuthor;
    
    @CreationTimestamp
    @Comment("업로드 시간")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Comment("업데이트 시간")
    private LocalDateTime updatedTime;

	@Column(nullable = true)
	@Comment("사진경로")
	private String photoSrc;
    
    public ScheduleResponseDTO toResponseDTO() {
        return ScheduleResponseDTO.builder()
                .id(this.id)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .scheduleTitle(this.title)
                .scheduleContent(this.content)
                .isComplete(this.isComplete)
                .color(this.color)
                .author(this.author)
                .animal(this.animal)
                .updateAuthor(this.updateAuthor)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .photoSrc(this.photoSrc)
                .build();
    }
    
    

    


}
