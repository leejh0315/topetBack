package topetBack.topetBack.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity // Member 라는 객체와 DB 테이블을 매핑합니다. JPA가 관리합니다.
public class Schedule {
	
	@Id // 해당 변수가 PK가 됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당 합니다.
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
