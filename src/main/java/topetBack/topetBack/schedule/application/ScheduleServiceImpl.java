package topetBack.topetBack.schedule.application;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;
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
    	System.out.println("scheduleRequestDTO.getImages().size() : " +  scheduleRequestDTO.getImages().size() );
    	System.out.println(scheduleRequestDTO.getImages());
    	if(scheduleRequestDTO.getImages().size()>0) {
    		FileGroupEntity fileGroupEntity = fileService.uploadPhoto(scheduleRequestDTO.getImages(), scheduleRequestDTO.toScheduleEntity().getFileGroupEntity(), FileCategory.SCHEDULE.getPath());
    		scheduleRequestDTO.setProfileSrc(fileGroupEntity.getFileList().get(0).getFilePath());
    	}
    	
    	ScheduleEntity scheduleEntity = scheduleRequestDTO.toScheduleEntity();
    	ScheduleEntity result = scheduleRepository.save(scheduleEntity);
    	return result.toResponseDTO();
    }

	@Override
	public List<ScheduleResponseDTO> findByAuthorId(Long authorId) {
		List<ScheduleEntity> scheduleList = scheduleRepository.findByAuthorId(authorId);
		return scheduleList.stream()
                .map(ScheduleEntity::toResponseDTO)
                .collect(Collectors.toList());

	}

	@Override
	public List<ScheduleResponseDTO> findByAnimalId(Long animalId) {
		List<ScheduleEntity> scheduleList = scheduleRepository.findByAnimalId(animalId);
		return scheduleList.stream()
                .map(ScheduleEntity::toResponseDTO)
                .collect(Collectors.toList());
		
	}

	@Override
	public void updateSchedule(ScheduleRequestDTO scheduleRequestDTO) {
		scheduleRequestDTO.setIsComplete(!(scheduleRequestDTO.getIsComplete()));
		scheduleRepository.save(scheduleRequestDTO.toScheduleEntity());
	}



}
