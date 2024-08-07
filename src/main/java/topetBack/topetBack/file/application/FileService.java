package topetBack.topetBack.file.application;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.shorts.domain.ShortsRequestDTO;


@Service
public interface FileService {

	FileGroupEntity uploadPhoto(List<MultipartFile> photos, FileGroupEntity fileGroupEntity, String middlePath) throws IOException;
	String uploadOnePhoto(MultipartFile image , String middlePath) throws IOException ;
	String[] uploadShorts(ShortsRequestDTO shortsRequestDTO, String middlePath) throws IOException;
}
