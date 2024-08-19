package topetBack.topetBack.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import topetBack.topetBack.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{


	Member save(Member member);
	
	
	
	
	



Optional<Member> findBySocialId(String socialId);
Member findByName(String name);
}