package topetBack.topetBack.pet.application;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import topetBack.topetBack.pet.domain.PetEntity;
import topetBack.topetBack.pet.domain.PetRequestDTO;
import topetBack.topetBack.pet.domain.PetResponseDTO;


@Service
public interface PetService {

	PetResponseDTO createPet(PetRequestDTO petRequestDTO, MultipartFile image) throws IOException;
	PetResponseDTO findById(Long petId);
	PetResponseDTO findByUid(String uid);
	PetEntity findEntityByUid(String uid);
	PetResponseDTO updatePet(PetRequestDTO petRequestDTO) throws IOException ;
	
	Long deleteMember(Long memberId, Long petId);
	
	Long deletePet(Long petId);
//	List<PetResponseDTO> findPetByMember(Long memberId);
}
