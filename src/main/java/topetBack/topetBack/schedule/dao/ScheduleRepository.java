package topetBack.topetBack.schedule.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.schedule.domain.ScheduleEntity;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;

@Repository
public interface ScheduleRepository  extends JpaRepository<ScheduleEntity, Long>{
	ScheduleEntity save(ScheduleEntity scheduleEntity);
	List<ScheduleEntity> findByAuthor(Member author);
}
