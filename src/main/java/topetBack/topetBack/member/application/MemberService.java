package topetBack.topetBack.member.application;




import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.dao.MemberPetRepository;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.*;
import topetBack.topetBack.pet.domain.PetEntity;

@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberPetRepository memberPetRepository;
	private final FileService fileService;
	
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	private final SessionManager sessionManager;

	public MemberService(MemberRepository memberRepository, MemberPetRepository memberPetRepository, FileService fileService, @Qualifier("sessionManager") SessionManager sessionManager) {
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
    
    public List<MemberPet> findByMember(Member member){
    	return memberPetRepository.findByMember(member);
    }
    
    public void saveMemberPet(Member member, PetEntity petEntity) {
    	MemberPet memberPet = new MemberPet(member, petEntity);
    	memberPetRepository.save(memberPet);
    }

	@Transactional
	public SessionMember updateMember(MemberRequestDTO memberRequestDTO){
		Member member = memberRepository.findById(memberRequestDTO.getId())
				.orElseThrow(() -> new RuntimeException("Member 가 없음"));

		member.updateMember(memberRequestDTO);

		Member updatedMember = memberRepository.save(member);

		return sessionManager.updateMember(updatedMember, memberRequestDTO.getCookie());

	}
    
    public Member userInfoRegister(Member member, String nickName, MultipartFile image) throws IOException {
    	SessionMember sessionMember = member.toSessionMember();
    	sessionMember.setName(nickName);
    	String profileSrc = fileService.uploadPhoto(image, FileCategory.MEMBER.getPath());
    	sessionMember.setProfileSrc(profileSrc);
    	Member newMember = memberRepository.save(sessionMember.toMember());
    	return newMember;
    }
    
}