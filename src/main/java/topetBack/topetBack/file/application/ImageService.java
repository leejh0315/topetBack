package topetBack.topetBack.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.domain.Image;
import topetBack.topetBack.repository.ImageRepository;

@RequiredArgsConstructor
@Service
public class ImageService {
	private final ImageRepository imageRepository;
	
//	@Transactional
//	public Long save(Image image) {
//		return imageRepository.save(image.createPost()).getFileId();
//	}
}
