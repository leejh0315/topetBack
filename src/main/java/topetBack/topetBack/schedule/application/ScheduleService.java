package topetBack.topetBack.service;

import org.springframework.stereotype.Service;

import topetBack.topetBack.repository.MemberRepository;
import topetBack.topetBack.repository.ScheduleRepository;

@Service
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	
	public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

}
