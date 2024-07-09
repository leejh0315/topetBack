package topetBack.topetBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.domain.Member;
import topetBack.topetBack.domain.Schedule;

public interface ScheduleRepository  extends JpaRepository<Member, Integer>{

	Schedule save(Schedule schedule);

}
