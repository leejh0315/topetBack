package topetBack.topetBack.pet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.application.MemberService;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.application.PetService;
import topetBack.topetBack.pet.dao.PetRepository;
import topetBack.topetBack.pet.domain.PetRequestDTO;
import topetBack.topetBack.pet.domain.PetResponseDTO;



@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/pet")
public class PetController {

	private static final Logger logger = LoggerFactory.getLogger(PetController.class);
	
	
	private final PetService petService;
	private final SessionManager sessionManager;
	private final PetRepository petRepository;
	private final MemberService memberService;

	@Transactional
	@PostMapping("/post")
	public ResponseEntity<PetResponseDTO> petRegistPost(@RequestParam(value="photo", required=false) MultipartFile image,
								@ModelAttribute PetRequestDTO petRequestDTO,
								HttpServletRequest req,
								HttpServletResponse resp
								) throws IOException {
	    Member sessionMember = sessionManager.getSessionObject(req).toMember();
	    petRequestDTO.setMember(sessionMember);

	    if(image!=null) {
		    List<MultipartFile> images = new ArrayList<MultipartFile>();
	        images.add(image);
	        petRequestDTO.setImage(images);
	    }
	    // PetService를 통해 Pet 등록
	    PetResponseDTO petResponseDTO = petService.createPet(petRequestDTO);
	    
	    sessionManager.refreshPetAdd(petResponseDTO, resp, req);
	    
	    return ResponseEntity.ok(petResponseDTO);
	}

	@Transactional
	@PostMapping("/petRegistrationTest")
	public ResponseEntity<PetResponseDTO> petRegistrationTest(@RequestParam(value="photo", required=false) MultipartFile image,
														@ModelAttribute PetRequestDTO petRequestDTO,
														HttpServletRequest req) throws IOException {
		if(image!=null) {
			List<MultipartFile> images = new ArrayList<MultipartFile>();
			images.add(image);
			petRequestDTO.setImage(images);
		}
		// PetService를 통해 Pet 등록
		PetResponseDTO petResponseDTO = petService.createPet(petRequestDTO);

		return ResponseEntity.ok(petResponseDTO);
	}

	@GetMapping("/getPet")
	public ResponseEntity<PetResponseDTO> getPet(@RequestParam(value = "id") Long id){

		PetResponseDTO pet = petService.findById(id);

		return ResponseEntity.ok(pet);
	}
}

