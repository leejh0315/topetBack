package topetBack.topetBack.schedule.application;

import java.io.IOException;

import org.springframework.stereotype.Service;

import topetBack.topetBack.schedule.domain.ScheduleRequestDTO;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;

@Service
public interface ScheduleService {

	ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO)throws IOException;
//	private final ScheduleRepository scheduleRepository;
//	
//	public ScheduleService(ScheduleRepository scheduleRepository) {
//        this.scheduleRepository = scheduleRepository;
//    }
//	
//	public ScheduleEntity saveSchedule(ScheduleEntity scheduleEntity) {
//    	 
//		scheduleRepository.save(scheduleEntity);
//		
//        return scheduleEntity;
//    }

}
