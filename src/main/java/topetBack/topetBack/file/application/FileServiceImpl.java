package topetBack.topetBack.file.application;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import topetBack.topetBack.file.dao.FileRepository;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.file.domain.FileGroupEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;

	@Value("${toPet.fileStorePath}")
	private String fileBasePath;

	public FileServiceImpl(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public FileGroupEntity uploadPhoto(List<MultipartFile> photos, FileGroupEntity fileGroupEntity, String middlePath)
			throws IOException {

		List<FileInfoEntity> fileInfoEntityList = new ArrayList<>();
		String baseDir = fileBasePath + middlePath;
		
		if (photos != null) {
			for (MultipartFile photo : photos) {
				try {
					// 파일 저장 경로 생성
					String fileName = photo.getOriginalFilename();
					String extension = fileName.substring(fileName.lastIndexOf("."));
					String newFileName = UUID.randomUUID().toString() + extension;
					Path filePath = Paths.get(baseDir, newFileName);
					// 디렉토리 생성
					Files.createDirectories(filePath.getParent());
					// 파일 저장
					Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

					// File 엔티티 생성
					FileInfoEntity FileInfoEntity = new FileInfoEntity();
					FileInfoEntity.setOrigFileName(fileName);
					FileInfoEntity.setFilePath(filePath.toString());
					FileInfoEntity.setFileSize(photo.getSize());
					FileInfoEntity.setFileGroupEntity(fileGroupEntity);
					FileInfoEntity.setNewFileName(newFileName);
					// 리스트에 추가
					fileInfoEntityList.add(FileInfoEntity);

				} catch (IOException e) {
					throw new IOException();
				}
			}
		}
		fileGroupEntity.setFileList(fileInfoEntityList);

		return fileGroupEntity;
	}

	@Override
	public String uploadOnePhoto(MultipartFile multipartFile, String middlePath) throws IOException {
        if(multipartFile != null) {
        String baseDir = fileBasePath + middlePath;
        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + extension;
        Path filePath = Paths.get(baseDir, newFileName);
        try {
        	Files.createDirectories(filePath.getParent());
    		Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);    // 업로드된 파일의 InputStream 얻기
        } catch (IOException e) {
            throw new IOException();
        }
        
		return filePath.toString();
        }else {
        	return null;
        }
        
	}

}
