package topetBack.topetBack.member.application;




import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.member.dao.MemberPetRepository;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberPet;
import topetBack.topetBack.member.domain.MemberRequestDTO;
import topetBack.topetBack.member.domain.MemberResponseDTO;
import topetBack.topetBack.pet.domain.PetEntity;
import topetBack.topetBack.pet.domain.PetResponseDTO;

@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberPetRepository memberPetRepository;
	private final FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	private final SessionManager sessionManager;
	

	public MemberService(MemberRepository memberRepository, 
			MemberPetRepository memberPetRepository, FileService fileService
			, @Qualifier("sessionManager") SessionManager sessionManager
//			,@Qualifier("memberPetRepositoryCustom") MemberPetRepositoryCustom memberPetRepositoryCustom
			) {
        this.memberRepository = memberRepository;
        this.memberPetRepository = memberPetRepository;
        this.fileService = fileService;
		this.sessionManager = sessionManager;
	
	
	}
    
    public Optional<Member> findBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId);
    }
    public Member memberJoin(Member memberInfo) {
    	Member member = new Member(
    			0L,
    			memberInfo.getSocialId(), 
				(String) memberInfo.getEmail(), 
				(String) memberInfo.getName(), 
    			"",
    			new FileGroupEntity());
        return memberRepository.save(member);
    }
    
//    public List<PetEntity> getByMemberIdPetEntity(Long id){
//    	return petRepository.getByMemberIdPetEntity(id);
//    }

	public MemberResponseDTO findById(Long id) {
		Optional<Member> member = memberRepository.findById(id);
		return member.orElseThrow().toResponseDTO();
	}
    
    public List<MemberPet> findByMember(Member member){
    	return memberPetRepository.findByMember(member);
    }
    
    public void saveMemberPet(Long memberId, PetEntity petEntity) {
    	Optional<Member> member = memberRepository.findById(memberId);
    	MemberPet memberPet = new MemberPet(member.get(), petEntity);
    	memberPetRepository.save(memberPet);
    }

	public MemberResponseDTO updateMember(MemberRequestDTO memberRequestDTO) throws IOException {
		Member member = memberRepository.findById(memberRequestDTO.getId())
				.orElseThrow(() -> new RuntimeException("Member 가 없음"));
		
		if(memberRequestDTO.getPhoto() != null && !memberRequestDTO.getPhoto().isEmpty()){
			System.out.println("포토잇ㄷ.ㅏ");
			FileInfoEntity fileInfoEntity =fileService.storeFile(memberRequestDTO.getPhoto(), FileCategory.MEMBER);
			memberRequestDTO.setProfileSrc(fileInfoEntity.getFilePath());
		}
		member.updateMember(memberRequestDTO);
		
		Member updatedMember = memberRepository.save(member);
		

		return updatedMember.toResponseDTO();

	}
    
//    public Member userInfoRegister(Member member, String nickName, MultipartFile image) throws IOException {
//    	SessionMember sessionMember = member.toSessionMember();
//    	sessionMember.setName(nickName);
//    	FileInfoEntity fileInfoEntity =fileService.storeFile(image, FileCategory.MEMBER);
//    	sessionMember.setProfileSrc(fileInfoEntity.getFilePath());
//    	
//    	Member newMember = memberRepository.save(sessionMember.toMember());
//    	return newMember;
//    }
    
    public List<PetResponseDTO> findPetByMember(Long memberId) {
		List<PetEntity> petEntity = memberPetRepository.findPetByMember(memberId);
		return petEntity
				.stream()
				.map(PetEntity::toResponseDTO)
				.collect(Collectors.toList());
	}
    @Transactional
    public void deleteById(Long memberId) {
    	memberRepository.deleteById(memberId);
    }
//    
}