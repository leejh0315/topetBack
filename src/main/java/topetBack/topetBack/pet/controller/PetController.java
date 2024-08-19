package topetBack.topetBack.pet.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.application.MemberService;
import topetBack.topetBack.pet.application.PetService;
import topetBack.topetBack.pet.dao.PetRepository;
import topetBack.topetBack.pet.domain.PetEntity;
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

	@PostMapping("/post")
	public ResponseEntity<PetResponseDTO> petRegistPost(
			@RequestParam(value = "photo", required = false) MultipartFile image, PetRequestDTO petRequestDTO,
			HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println(image);
		// PetService를 통해 Pet 등록
		PetResponseDTO petResponseDTO = petService.createPet(petRequestDTO, image);
		return ResponseEntity.ok(petResponseDTO);
	}

	@GetMapping("/getMyPets/{id}")
	public ResponseEntity<List<PetResponseDTO>> getMyPets(@PathVariable("id") Long id) {
		List<PetResponseDTO> myPets = memberService.findPetByMember(id);
		System.out.println(myPets);
		return ResponseEntity.ok(myPets);
	}

	@GetMapping("/petProfileDetail/{id}")
	public ResponseEntity<PetResponseDTO> petProfileDetail(@PathVariable("id") Long id) {
		PetResponseDTO pet = petService.findById(id);
		System.out.println(pet);
		return ResponseEntity.ok(pet);
	}

	@PostMapping("/update")
	public ResponseEntity<PetResponseDTO> updatePet(PetRequestDTO petRequestDTO) throws IOException {

		System.out.println("petRequestDTO : " + petRequestDTO);
		PetResponseDTO petResponseDTO = petService.updatePet(petRequestDTO);

		return ResponseEntity.ok(petResponseDTO);
	}

	@PostMapping("/postAddPet")
	public ResponseEntity<PetResponseDTO> addPet(@RequestBody Map<String, String> uid, HttpServletRequest req,
			HttpServletResponse resp) throws JsonMappingException, JsonProcessingException {

		Long memberId = sessionManager.getSessionObject(req);

		PetResponseDTO pet = petService.findByUid(uid.get("uid"));
		if (pet != null) {
			PetEntity petEntity = petService.findEntityByUid(uid.get("uid"));
			memberService.saveMemberPet(memberId, petEntity);
		}
		return ResponseEntity.ok(pet);
	}

	@PostMapping("/deleteMember")
	public Long deleteMember(@RequestBody Map<String, Long> data) {
		Long deleteQuery = petService.deleteMember(data.get("memberId"), data.get("petId"));
		return deleteQuery;
	}
	
	@PostMapping("/deletePet/{memberId}/{petId}")
	public ResponseEntity<List<PetResponseDTO>> deletePet(@PathVariable("memberId") Long memberId, @PathVariable("petId") Long petId) {
		Long deleteQuery = petService.deletePet(memberId ,petId);
		if(deleteQuery > 0) {
			List<PetResponseDTO> myPets = memberService.findPetByMember(memberId);
			return ResponseEntity.ok(myPets);
		}else {
			return ResponseEntity.ok(null);
		}
	}
	

}
