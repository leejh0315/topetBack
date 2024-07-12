package topetBack.topetBack.schedule.application;

import org.springframework.stereotype.Service;

import topetBack.topetBack.schedule.dao.ScheduleRepository;
import topetBack.topetBack.schedule.domain.ScheduleEntity;

@Service
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	
	public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
	public ScheduleEntity saveSchedule(ScheduleEntity scheduleEntity) {
    	 
		scheduleRepository.save(scheduleEntity);
		
        return scheduleEntity;
    }

}
