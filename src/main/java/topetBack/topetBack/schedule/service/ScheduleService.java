package topetBack.topetBack.schedule.application;

import org.springframework.stereotype.Service;

import topetBack.topetBack.domain.Schedule;
import topetBack.topetBack.repository.ScheduleRepository;

@Service
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	
	public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
	public Schedule saveSchedule(Schedule schedule) {
    	 
		scheduleRepository.save(schedule);
		
        return schedule;
    }

}
