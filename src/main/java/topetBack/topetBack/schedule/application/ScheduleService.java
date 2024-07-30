package topetBack.topetBack.schedule.application;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.schedule.domain.ScheduleEntity;
import topetBack.topetBack.schedule.domain.ScheduleRequestDTO;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;

@Service
public interface ScheduleService {

	ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO)throws IOException;
	List<ScheduleResponseDTO> findByAuthorId(Long authorId);
	List<ScheduleResponseDTO> findByAnimalId(Long animalId);

}
