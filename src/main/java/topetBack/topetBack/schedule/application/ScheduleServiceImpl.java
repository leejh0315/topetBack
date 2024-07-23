package topetBack.topetBack.schedule.application;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.schedule.dao.ScheduleRepository;
import topetBack.topetBack.schedule.domain.ScheduleEntity;
import topetBack.topetBack.schedule.domain.ScheduleRequestDTO;
import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	private final ScheduleRepository scheduleRepository;
	private final FileService fileService;
	private final EntityManager entityManager;
	
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, FileService fileService, EntityManager entityManager	) {
        this.scheduleRepository = scheduleRepository;
        this.fileService = fileService;
        this.entityManager = entityManager;
    }
	    
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO) throws IOException{
    	ScheduleEntity scheduleEntity = scheduleRequestDTO.toScheduleEntity();
    	
    	if(scheduleEntity.getFileGroupEntity() != null) {
    		entityManager.merge(scheduleEntity.getFileGroupEntity());
    		fileService.uploadPhoto(scheduleRequestDTO.getImages(), scheduleEntity.getFileGroupEntity(), FileCategory.SCHEDULE.getPath());
    	}
    	
    	ScheduleEntity result = scheduleRepository.save(scheduleEntity);
    	return result.toResponseDTO();
    }

	@Override
	public List<ScheduleResponseDTO> findByAuthor(Member author) {
		List<ScheduleEntity> scheduleList = scheduleRepository.findByAuthor(author);
		return scheduleList.stream()
                .map(ScheduleEntity::toResponseDTO)
                .collect(Collectors.toList());

	}

}
