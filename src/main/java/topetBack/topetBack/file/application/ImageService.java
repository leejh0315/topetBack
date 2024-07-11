package topetBack.topetBack.file.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.AssertFalse.List;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.file.dao.ImageRepository;
import topetBack.topetBack.file.domain.Image;


@Service
public interface ImageService {
	public String communityPostPhoto(MultipartFile[] photos);
}
