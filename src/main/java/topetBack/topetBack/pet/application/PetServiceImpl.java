package topetBack.topetBack.pet.application;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.dao.MemberPetRepository;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberPet;
import topetBack.topetBack.member.domain.MemberResponseDTO;
import topetBack.topetBack.pet.dao.PetRepository;
import topetBack.topetBack.pet.domain.PetEntity;
import topetBack.topetBack.pet.domain.PetRequestDTO;
import topetBack.topetBack.pet.domain.PetResponseDTO;

@Service
@Slf4j
@Transactional
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;
    private final MemberPetRepository memberPetRepository;
	private final FileService fileService;

    public PetServiceImpl(PetRepository petRepository, MemberPetRepository memberPetRepository, FileService fileService) {
        this.petRepository = petRepository;
        this.memberPetRepository  = memberPetRepository;
        this.fileService = fileService;
    }
	
    @Override
    @Transactional
    public PetResponseDTO createPet(PetRequestDTO petRequestDTO) throws IOException {

    	String uid = createKey();
    	petRequestDTO.setUID(uid);
    	
        if(petRequestDTO.getImage() != null) {
        	FileGroupEntity fileGroupEntity = fileService.uploadPhoto(petRequestDTO.getImage(), petRequestDTO.toPetEntity().getFileGroupEntity(), FileCategory.PET.getPath());
        	petRequestDTO.setProfileSrc(fileGroupEntity.getFileList().get(0).getFilePath());
        }
        
        PetEntity result = petRepository.save(petRequestDTO.toPetEntity());
        if(petRequestDTO.getMember() != null){
            MemberPet memberPet = new MemberPet(petRequestDTO.getMember(), result);
            memberPetRepository.save(memberPet);
        }
        log.info("petEntity : " + result.toString());
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

    @Override
    public PetResponseDTO findById(Long id) {
    	Optional<PetEntity> pet = petRepository.findById(id);
    	
        return pet.map(petEntity -> {
            PetResponseDTO petResponseDTO = petEntity.toResponseDTO();

            List<Member> members = memberPetRepository.findMemberByPet(id);

            List<MemberResponseDTO> membersDTO = members.stream()
                    .map(Member::toResponseDTO)
                    .collect(Collectors.toList());

            petResponseDTO.setMember(membersDTO);

            return petResponseDTO;
        }).orElse(null);
    }

}
