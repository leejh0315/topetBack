package topetBack.topetBack.schedule.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.schedule.domain.Schedule;
import topetBack.topetBack.user.domain.Member;

public interface ScheduleRepository  extends JpaRepository<Member, Integer>{

	Schedule save(Schedule schedule);

}
