package topetBack.topetBack.pet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.application.PetService;
import topetBack.topetBack.pet.domain.PetRequestDTO;
import topetBack.topetBack.pet.domain.PetResponseDTO;



@RestController
@RequiredArgsConstructor
@Slf4j
public class PetController {

	private static final Logger logger = LoggerFactory.getLogger(PetController.class);
	
	
	private final PetService petService;
	private final SessionManager sessionManager;
	
	@Transactional
	@PostMapping("/petRegistration")
	public String petRegistPost(@RequestParam(value="photo", required=false) MultipartFile image,
								@ModelAttribute PetRequestDTO petRequestDTO,
								HttpServletRequest req
								) throws IOException {
		
		log.info("petRegistration : " + petRequestDTO);
	    Member sessionMember = (Member) sessionManager.getSessionObject(req);
	    
	    List<Member> members = new ArrayList<>();
	    members.add(sessionMember);
	    petRequestDTO.setMember(members);
	    
	    // PetService를 통해 Pet 등록
	    PetResponseDTO petResponseDTO = petService.createPet(petRequestDTO);
	    
	    
		log.info("petRequestDTO" + petRequestDTO);
		log.info("petResponseDTO" + petResponseDTO);
		
		return "";
	}
}

