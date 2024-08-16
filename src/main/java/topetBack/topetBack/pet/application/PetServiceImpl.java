package topetBack.topetBack.pet.application;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileInfoEntity;
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
//    private final PetOwnerRepository petOwnerRepository;
    private final MemberPetRepository memberPetRepository;
	private final FileService fileService;
//	private final MemberPetRepositoryCustom memberPetRepositoryCustom;

    public PetServiceImpl(PetRepository petRepository, MemberPetRepository memberPetRepository, FileService fileService
//    		, PetOwnerRepository petOwnerRepository
//    		,@Qualifier("memberPetRepositoryCustom") MemberPetRepositoryCustom memberPetRepositoryCustom
    		) {
        this.petRepository = petRepository;
        this.memberPetRepository  = memberPetRepository;
        this.fileService = fileService;
//        this.petOwnerRepository = petOwnerRepository;
//        this.memberPetRepositoryCustom = memberPetRepositoryCustom;
    }
	
    @Override
    @Transactional
    public PetResponseDTO createPet(PetRequestDTO petRequestDTO, MultipartFile image) throws IOException {

    	String uid = createKey();
    	petRequestDTO.setUid(uid);
    	
        if(image != null) {
        	FileInfoEntity fileInfoEntity = fileService.storeFile(image, FileCategory.PET);
        	petRequestDTO.setProfileSrc(fileInfoEntity.getFilePath());
        }
        petRequestDTO.setOwnerId(petRequestDTO.getMember().getId());
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
    @Transactional
    public PetResponseDTO findById(Long id) {
    	Optional<PetEntity> pet = petRepository.findById(id);
    	
        return pet.map(petEntity -> {
            PetResponseDTO petResponseDTO = petEntity.toResponseDTO();

            List<Member> members = memberPetRepository.findMemberByPet(id);

            List<MemberResponseDTO> membersDTO = members.stream()
                    .map(Member::toResponseDTO)
                    .collect(Collectors.toList());

            petResponseDTO.setMembers(membersDTO);

            return petResponseDTO;
        }).orElse(null);
    }

	@Override
	@Transactional
	public PetResponseDTO findByUid(String uid) {
		Optional<PetEntity> pet = petRepository.findByUid(uid);
		System.out.println("petServiceIMPL pet : " + pet);
		
		if(pet.isEmpty()){
			return null;
		}else {
			return pet.get().toResponseDTO();
		}
		
	}

	@Override
	@Transactional
	public PetEntity findEntityByUid(String uid) {
		return petRepository.getByUid(uid);
	}
	
	@Override
	public PetResponseDTO updatePet(PetRequestDTO petRequestDTO) throws IOException {
		if(petRequestDTO.getPhoto() != null && !petRequestDTO.getPhoto().isEmpty()){
			System.out.println("포토잇ㄷ.ㅏ");
			FileInfoEntity fileInfoEntity =fileService.storeFile(petRequestDTO.getPhoto(), FileCategory.PET);
			petRequestDTO.setProfileSrc(fileInfoEntity.getFilePath());
		}
		
		PetEntity updatedPet = petRepository.save(petRequestDTO.toPetEntity());
		
		
		return updatedPet.toResponseDTO();
	}

	@Override
	public Long deleteMember(Long memberId, Long petId) {
		// TODO Auto-generated method stub
		Long deleteQuery = memberPetRepository.deleteMember(memberId, petId);
		return deleteQuery;
	}

//	@Override
//	public List<PetResponseDTO> findPetByMember(Long memberId) {
//		List<PetEntity> petEntity = memberPetRepositoryCustom.findPetByMember(memberId);
//		return petEntity
//				.stream()
//				.map(PetEntity::toResponseDTO)
//				.collect(Collectors.toList());
//	}

}
