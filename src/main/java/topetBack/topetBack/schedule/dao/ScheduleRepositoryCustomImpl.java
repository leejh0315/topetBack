package topetBack.topetBack.schedule.dao;


import static topetBack.topetBack.schedule.domain.QScheduleEntity.scheduleEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import topetBack.topetBack.schedule.domain.ScheduleEntity;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;

@Repository
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom{

	private final JPAQueryFactory queryFactory;
	
	public ScheduleRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }   
	
	@Override
	public List<ScheduleResponseDTO> getTodaySchedules(Long animalId) {
		LocalDate today = LocalDate.now();
		LocalDateTime startOfToday = today.atStartOfDay(); 
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX); 
		
		JPAQuery<ScheduleEntity> query = queryFactory
				.select(scheduleEntity)
				.from(scheduleEntity)
				.where(
						(scheduleEntity.startDate.loe(endOfToday).and
						(scheduleEntity.endDate.goe(startOfToday)))
						.and(
								scheduleEntity.animal.id.eq(animalId))
								
						);
		 List<ScheduleEntity> schedules = query.fetch();
		return schedules.stream()
                .map(ScheduleEntity::toResponseDTO) // 또는 적절한 변환 메소드 사용
                .collect(Collectors.toList());
	}

}
