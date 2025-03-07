package topetBack.topetBack.schedule.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import lombok.Data;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.domain.PetEntity;

@Data
public class ScheduleRequestDTO {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String scheduleTitle; // 이름 수정 필요
    private String scheduleContent; // 이름 수정 필요
    private Boolean isComplete;
    private String color;
    private Member author;
    private Member updateAuthor;
    private PetEntity animal;
    private LocalDateTime createdTime;
	private String photoSrc;
	
    public ScheduleEntity toScheduleEntity() {
        FileGroupEntity fileGroupEntity = new FileGroupEntity();

        return ScheduleEntity.builder()
                .id(this.id)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .title(this.scheduleTitle) // 필드명 수정 필요
                .content(this.scheduleContent) // 필드명 수정 필요
                .isComplete(this.isComplete)
                .color(this.color)
                .author(this.author)
                .animal(this.animal)
                .createdTime(this.createdTime)
                .updateAuthor(this.updateAuthor)
                .photoSrc(this.photoSrc)
                .build();
    }
}
