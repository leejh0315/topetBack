package topetBack.topetBack.file.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import topetBack.topetBack.file.domain.FileGroupEntity;

import java.io.IOException;
import java.util.List;


@Service
public interface FileService {

	FileGroupEntity uploadPhoto(List<MultipartFile> photos, FileGroupEntity fileGroupEntity, String middlePath) throws IOException;
}
