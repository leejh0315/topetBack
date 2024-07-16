package topetBack.topetBack.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	Optional<Member> findBySocialId(String socialId);
	Member findByName(String name);

}
