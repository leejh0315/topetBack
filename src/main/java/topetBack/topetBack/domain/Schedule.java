package topetBack.topetBack.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Schedule {
	private int scheduleId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String scheduleTitle;
	private String scheduleContent;
	private boolean isComplete;
	private String color;
	private int writerId;
	private int editorId;
}
