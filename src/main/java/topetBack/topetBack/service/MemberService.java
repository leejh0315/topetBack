package topetBack.topetBack.service;




import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import topetBack.topetBack.domain.Member;
import topetBack.topetBack.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    public Member memberJoin(Member memberInfo) {
    	Member member = new Member( memberInfo.getMemberKid(), 
				(String) memberInfo.getMemberEmail(), 
				(String) memberInfo.getMemberName());
        return memberRepository.save(member);
    }
    
    public Optional<Member> selectByKakaoId(long kakaoId) {
        return memberRepository.findByMemberKid(kakaoId);
    }

}
