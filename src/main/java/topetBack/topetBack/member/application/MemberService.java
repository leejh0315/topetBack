package topetBack.topetBack.member.application;




import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import topetBack.topetBack.member.dao.MemberPetRepository;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberPet;
import topetBack.topetBack.pet.dao.PetRepository;
import topetBack.topetBack.pet.domain.PetEntity;

@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberPetRepository memberPetRepository;
	
	
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public MemberService(MemberRepository memberRepository, MemberPetRepository memberPetRepository) {
        this.memberRepository = memberRepository;
        this.memberPetRepository = memberPetRepository;
    }
    
    public Optional<Member> findBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId);
    }
    public Member memberJoin(Member memberInfo) {
    	Member member = new Member(
    			0L,
    			memberInfo.getSocialId(), 
				(String) memberInfo.getEmail(), 
				(String) memberInfo.getName());
        return memberRepository.save(member);
    }
    
//    public List<PetEntity> getByMemberIdPetEntity(Long id){
//    	return petRepository.getByMemberIdPetEntity(id);
//    }
    
    public List<MemberPet> findByMember(Member member){
    	return memberPetRepository.findByMember(member);
    }
    

    
}
