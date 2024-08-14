package topetBack.topetBack.schedule.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.community.dao.CommunityRepositoryCustom;
import topetBack.topetBack.schedule.domain.ScheduleEntity;

@Repository
public interface ScheduleRepository  extends JpaRepository<ScheduleEntity, Long> ,ScheduleRepositoryCustom{
	ScheduleEntity save(ScheduleEntity scheduleEntity);
	Optional<ScheduleEntity> findById(Long Id);
	List<ScheduleEntity> findByAuthorId(Long authorId);
	
	List<ScheduleEntity> findByAnimalId(Long animalId);

}
