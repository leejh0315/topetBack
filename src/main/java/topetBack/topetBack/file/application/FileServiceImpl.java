package topetBack.topetBack.file.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import topetBack.topetBack.file.dao.FileRepository;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.shorts.domain.ShortsRequestDTO;

@Service
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;
	private final AmazonS3Client amazonS3Client;

	@Value("${toPet.fileStorePath}")
	private String fileBasePath;

	@Value("${spring.s3.bucket}")
	private String bucketName;

	@Value("${spring.s3.accessKey}")
	private String keyName;

	public FileServiceImpl(FileRepository fileRepository, AmazonS3Client amazonS3Client) {
		this.fileRepository = fileRepository;
		this.amazonS3Client = amazonS3Client;
	}

//	@Override
//	public FileGroupEntity uploadPhotos(List<MultipartFile> photos, FileGroupEntity fileGroupEntity, String middlePath)
//			throws IOException {
//
//		List<FileInfoEntity> fileInfoEntityList = new ArrayList<>();
//		String baseDir = fileBasePath + middlePath;
//
//		if (photos != null) {
//			for (MultipartFile photo : photos) {
//				try {
//					// 파일 저장 경로 생성
//					String fileName = photo.getOriginalFilename();
//					String extension = fileName.substring(fileName.lastIndexOf("."));
//					String newFileName = UUID.randomUUID().toString() + extension;
//					Path filePath = Paths.get(baseDir, newFileName);
//					// 디렉토리 생성
//					Files.createDirectories(filePath.getParent());
//					// 파일 저장
//					Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//					// File 엔티티 생성
//					FileInfoEntity FileInfoEntity = new FileInfoEntity();
//					FileInfoEntity.setOrigFileName(fileName);
//					FileInfoEntity.setFilePath(filePath.toString());
//					FileInfoEntity.setFileSize(photo.getSize());
//					FileInfoEntity.setFileGroupEntity(fileGroupEntity);
//					FileInfoEntity.setNewFileName(newFileName);
//					// 리스트에 추가
//					fileInfoEntityList.add(FileInfoEntity);
//
//				} catch (IOException e) {
//					throw new IOException();
//				}
//			}
//		}
//		fileGroupEntity.setFileList(fileInfoEntityList);
//
//		return fileGroupEntity;
//	}

	@Override
	public FileInfoEntity storeFile(MultipartFile file, FileCategory fileCategory) throws IOException {
	    String baseDir = fileBasePath + fileCategory.getPath();
	    System.out.println("baseDir : " + baseDir);
	    
	    String fileName = file.getOriginalFilename();
	    String extension = fileName.substring(fileName.lastIndexOf("."));
	    String newFileName = UUID.randomUUID() + extension;
	    
	    // Set S3 key to include the file category path
	    String keyName = fileCategory.getPath() + newFileName;
	    System.out.println("keyName : " + keyName);
	    
	    String fileUrl;
	    try {
	        ObjectMetadata objectMetadata = new ObjectMetadata();
	        objectMetadata.setContentLength(file.getSize());
	        objectMetadata.setContentType(file.getContentType());
	        
	        amazonS3Client.putObject(
	            new PutObjectRequest(bucketName, keyName, file.getInputStream(), objectMetadata)
	                .withCannedAcl(CannedAccessControlList.PublicRead));
	        
	        fileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();
	        System.out.println("fileUrl : " + fileUrl);
	        
	    } catch (IOException e) {
	        throw new IOException("Failed to store file", e);
	    }

	    FileInfoEntity fileInfoEntity = new FileInfoEntity();
	    fileInfoEntity.setOrigFileName(fileName);
	    fileInfoEntity.setFilePath(fileUrl);
	    fileInfoEntity.setFileSize(file.getSize());
	    fileInfoEntity.setNewFileName(newFileName);
	    
	    System.out.println(fileInfoEntity.getOrigFileName());
	    System.out.println(fileInfoEntity.getFilePath());
	    System.out.println(fileInfoEntity.getNewFileName());
	    
	    fileRepository.save(fileInfoEntity);

	    return fileInfoEntity;
	}


	@Override
	public String uploadPhoto(MultipartFile multipartFile, String middlePath) throws IOException {
		if (multipartFile != null) {
			String baseDir = fileBasePath + middlePath;
			String fileName = multipartFile.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = UUID.randomUUID() + extension;
			Path filePath = Paths.get(baseDir, newFileName);
			String uploadFileUrl = "";
			
			try {
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentLength(multipartFile.getSize());
				objectMetadata.setContentType(multipartFile.getContentType());

				amazonS3Client.putObject(
						new PutObjectRequest(bucketName, keyName, multipartFile.getInputStream(), objectMetadata)
								.withCannedAcl(CannedAccessControlList.PublicRead));

				uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString(); // S3에서 파일 URL을 가져옴// 얻기
			} catch (IOException e) {
				throw new IOException();
			}

			return uploadFileUrl.toString();
		} else {
			return null;
		}

	}

	public String[] uploadShorts(ShortsRequestDTO shortsRequestDTO, String middlePath) throws IOException {
		String baseDir = fileBasePath + middlePath;

		String fileName = shortsRequestDTO.getThumbnailPhoto().getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		String newFileName = UUID.randomUUID() + extension;
		Path filePath = Paths.get(baseDir, newFileName);

		String videoBaseDir = baseDir + "/video/";
		String videoFileName = shortsRequestDTO.getVideo().getOriginalFilename();
		String videoExtension = videoFileName.substring(videoFileName.lastIndexOf("."));
		String newVideoFileName = UUID.randomUUID() + videoExtension;
		Path videoFilePath = Paths.get(videoBaseDir, newVideoFileName);
		
		
		

		try {
			// Create directories for thumbnail
			Files.createDirectories(filePath.getParent());
			Files.copy(shortsRequestDTO.getThumbnailPhoto().getInputStream(), filePath,
					StandardCopyOption.REPLACE_EXISTING);

			// Create directories for video
			Files.createDirectories(videoFilePath.getParent());
			Files.copy(shortsRequestDTO.getVideo().getInputStream(), videoFilePath,
					StandardCopyOption.REPLACE_EXISTING); // 수정된 부분

		} catch (IOException e) {
			// 예외 발생 시 로그를 남기고, 예외 메시지를 추가하여 더 유용한 정보를 제공합니다.
			throw new IOException("파일 업로드 중 오류 발생: " + e.getMessage(), e);
		}

		String[] filePaths = { filePath.toString(), videoFilePath.toString() };
		return filePaths;
	}

	@Override
	public FileGroupEntity uploadPhoto(List<MultipartFile> photos, FileGroupEntity fileGroupEntity, String middlePath)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

