package topetBack.topetBack.pet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.pet.domain.PetDomain;



@RestController
@RequiredArgsConstructor
@Slf4j
public class PetController {

	private static final Logger logger = LoggerFactory.getLogger(PetController.class);
	
	@PostMapping("/petRegistration")
	public String petRegistPost(@RequestParam(value="photo", required=false) MultipartFile image,
								@ModelAttribute PetDomain petDomain) {
		System.out.println(image);
		System.out.println(petDomain);
		
		return "";
	}
}
