package topetBack.topetBack.schedule.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileResponseDTO;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.domain.PetEntity;

@Data
@Builder
public class ScheduleResponseDTO {
	private Long id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String scheduleTitle;
	private String scheduleContent;
	private Boolean isComplete;
	private String color;
	private Member author;
	private PetEntity animal;
	private Member updateAuthor;
	
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private List<FileResponseDTO> images;
	private String profileSrc;
	
}
