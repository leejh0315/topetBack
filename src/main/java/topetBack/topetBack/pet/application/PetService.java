package topetBack.topetBack.pet.application;

import java.io.IOException;

import org.springframework.stereotype.Service;

import topetBack.topetBack.pet.domain.PetRequestDTO;
import topetBack.topetBack.pet.domain.PetResponseDTO;


@Service
public interface PetService {

	PetResponseDTO createPet(PetRequestDTO petRequestDTO) throws IOException;
}
