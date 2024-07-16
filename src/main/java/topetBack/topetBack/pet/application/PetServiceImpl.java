//package topetBack.topetBack.pet.application;
//
//import java.io.IOException;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import lombok.RequiredArgsConstructor;
//import topetBack.topetBack.file.application.FileService;
//import topetBack.topetBack.file.domain.FileCategory;
//import topetBack.topetBack.pet.dao.PetRepository;
//import topetBack.topetBack.pet.domain.PetEntity;
//import topetBack.topetBack.pet.domain.PetRequestDTO;
//import topetBack.topetBack.pet.domain.PetResponseDTO;
//
//@RequiredArgsConstructor
//public class PetServiceImpl implements PetService{
//
//    private final PetRepository petRepository;
//	private final FileService fileService;
//	
////    @Override
////    @Transactional
////    public PetResponseDTO createPet(PetRequestDTO petRequestDTO) throws IOException {
////        PetEntity petEntity = petRequestDTO.toPetEntity();
////
////        fileService.uploadPhoto(petRequestDTO.getImage(), communityEntity.getFileGroupEntity(), FileCategory.COMMUNITY.getPath());
//////
//////        CommunityEntity result = communityRepository.save(communityEntity);
////
////        return 
////    }
//
//}
