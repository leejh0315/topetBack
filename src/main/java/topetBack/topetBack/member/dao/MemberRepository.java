package topetBack.topetBack.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import topetBack.topetBack.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

//	Optional<SessionMember> findBySocialId(String socialId);
	Optional<Member> findBySocialId(String socialId);
	Member findByName(String name);
	@Query("SELECT m FROM Member m JOIN FETCH m.pets WHERE m.id = :id")
    Member findByIdWithPets(@Param("id") Long id);

}

