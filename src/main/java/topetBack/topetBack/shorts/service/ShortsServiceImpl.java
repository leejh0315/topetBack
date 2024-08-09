package topetBack.topetBack.shorts.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.shorts.dao.ShortsRepository;
import topetBack.topetBack.shorts.domain.ShortsEntity;
import topetBack.topetBack.shorts.domain.ShortsRequestDTO;
import topetBack.topetBack.shorts.domain.ShortsResponseDTO;

@Service
@RequiredArgsConstructor
public class ShortsServiceImpl implements ShortsService{

	
	private final FileService fileService;
	private final ShortsRepository shortsRepository;
	
	@Override
	@Transactional
	public ShortsResponseDTO shortsSave(ShortsRequestDTO shortsRequestDTO) throws IOException {
		
		
//		String[] paths = fileService.uploadShorts(shortsRequestDTO, FileCategory.SHORTS.getPath());
//		shortsRequestDTO.setThumbnailPhotoSrc(paths[0]);
//		shortsRequestDTO.setVideoSrc(paths[1]);
//		ShortsEntity shortsEntity = shortsRepository.save(shortsRequestDTO.toShortsEntity());
//		
		
		
		FileInfoEntity imageInfoEntity = fileService.storeFile(shortsRequestDTO.getThumbnailPhoto(), FileCategory.SHORTSTUMBNAIL);
		System.out.println(imageInfoEntity.getFilePath());
		shortsRequestDTO.setThumbnailPhotoSrc(imageInfoEntity.getFilePath());
		
		FileInfoEntity videoInfoEntity = fileService.storeFile(shortsRequestDTO.getVideo(), FileCategory.SHORTS);
		System.out.println(videoInfoEntity.getFilePath());
		shortsRequestDTO.setVideoSrc(videoInfoEntity.getFilePath());
		
		
		
		
		ShortsEntity shortsEntity = shortsRepository.save(shortsRequestDTO.toShortsEntity());
		
		
		return shortsEntity.toResponseDTO();	
	}
	
	

	@Override
	public List<ShortsResponseDTO> getAll() {
		List<ShortsEntity> allShorts = shortsRepository.findAll();
		return allShorts.stream()
                .map(ShortsEntity::toResponseDTO)
                .collect(Collectors.toList());
		
	}

	@Override
	public ShortsResponseDTO getShortsDetail(Long id) {
		Optional<ShortsEntity> shortsEntity = shortsRepository.findById(id);
		if(shortsEntity.isEmpty()) {
			return null;
		}else {
			return shortsEntity.get().toResponseDTO();
		}
		
	}

	@Override
	public Long getRandomShorts() {
		Long randomShorts = shortsRepository.findRandomShort();
		return randomShorts;
	}

}
