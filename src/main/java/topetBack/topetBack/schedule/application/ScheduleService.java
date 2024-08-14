package topetBack.topetBack.schedule.application;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import topetBack.topetBack.schedule.domain.ScheduleRequestDTO;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;

@Service
public interface ScheduleService {

	ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO, MultipartFile image)throws IOException;
	List<ScheduleResponseDTO> findByAuthorId(Long authorId);
	List<ScheduleResponseDTO> findByAnimalId(Long animalId);
	void updateSchedule(ScheduleRequestDTO scheduleRequestDTO);
	List<ScheduleResponseDTO> findTodaySchedules(Long animalId);

}
