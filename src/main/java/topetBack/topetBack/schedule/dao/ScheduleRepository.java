package topetBack.topetBack.schedule.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.schedule.domain.ScheduleEntity;
import topetBack.topetBack.member.domain.Member;

public interface ScheduleRepository  extends JpaRepository<Member, Integer>{

	ScheduleEntity save(ScheduleEntity scheduleEntity);

}
