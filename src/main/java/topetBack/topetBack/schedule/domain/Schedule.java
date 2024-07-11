package topetBack.topetBack.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Schedule {
	private int scheduleId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String scheduleTitle;
	private String scheduleContent;
	
	@NotNull
	private boolean isComplete;
	
	private String color;
	private int writerId;
	private int editorId;
}
