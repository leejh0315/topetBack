package topetBack.topetBack.member.application;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.dao.MemberPetRepository;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberPet;
import topetBack.topetBack.member.domain.SessionMember;
import topetBack.topetBack.pet.domain.PetEntity;

@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberPetRepository memberPetRepository;
	private final FileService fileService;
	
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public MemberService(MemberRepository memberRepository, MemberPetRepository memberPetRepository, FileService fileService) {
        this.memberRepository = memberRepository;
        this.memberPetRepository = memberPetRepository;
        this.fileService = fileService;
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
    
    public Member userInfoRegister(Member member, String nickName, MultipartFile image) throws IOException {
    	SessionMember sessionMember = member.toSessionMember();
    	sessionMember.setName(nickName);
    	String profileSrc = fileService.uploadOnePhoto(image, FileCategory.MEMBER.getPath());
    	sessionMember.setProfileSrc(profileSrc);
    	Member newMember = memberRepository.save(sessionMember.toMember());
    	return newMember;
    }
    
}
