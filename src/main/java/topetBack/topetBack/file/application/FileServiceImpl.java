package topetBack.topetBack.file.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import topetBack.topetBack.file.dao.FileRepository;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.file.domain.FileGroupEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;

    @Value("${toPet.fileStorePath}")
    private String fileBasePath;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileGroupEntity uploadPhoto(List<MultipartFile> photos, FileGroupEntity fileGroupEntity, String middlePath) throws IOException {
        List<FileInfoEntity> fileInfoEntityList = new ArrayList<>();

        String baseDir = fileBasePath + middlePath;

        for (MultipartFile photo : photos) {
            try {
                // 파일 저장 경로 생성
                String fileName = photo.getOriginalFilename();
                Path filePath = Paths.get(baseDir, fileName);

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

                // 리스트에 추가
                fileInfoEntityList.add(FileInfoEntity);
            } catch (IOException e) {
                throw new IOException();
            }
        }

        fileGroupEntity.setFileList(fileInfoEntityList);

        return fileGroupEntity;
    }

}
