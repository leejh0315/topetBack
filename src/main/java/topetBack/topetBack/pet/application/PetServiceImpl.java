package topetBack.topetBack.pet.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.pet.dao.PetRepository;
import topetBack.topetBack.pet.domain.PetEntity;
import topetBack.topetBack.pet.domain.PetRequestDTO;
import topetBack.topetBack.pet.domain.PetResponseDTO;

@Service
@Slf4j
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;
	private final FileService fileService;

    public PetServiceImpl(PetRepository petRepository, FileService fileService) {
        this.petRepository = petRepository;
        this.fileService = fileService;
    }
	
    @Override
    @Transactional
    public PetResponseDTO createPet(PetRequestDTO petRequestDTO) throws IOException {
    	petRequestDTO.setUID(createKey());
    	log.info("petRequestDTO : " + petRequestDTO.toString());
        PetEntity petEntity = petRequestDTO.toPetEntity();
        log.info("petEntity : " + petEntity.toString());
        
        fileService.uploadPhoto(petRequestDTO.getImage(), petEntity.getFileGroupEntity(), FileCategory.PET.getPath());
        PetEntity result = petRepository.save(petEntity);
        return result.toResponseDTO();
        
    }

    //고유번호 생성
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random(System.currentTimeMillis());
 
        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤
 
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
}
