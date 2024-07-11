package topetBack.topetBack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	Optional<Member> findByMemberKid(long memberKid);
}
