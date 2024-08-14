package topetBack.topetBack.member.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberPet;

import java.util.List;
import java.util.Optional;

public interface MemberPetRepository extends JpaRepository<MemberPet, Long>, MemberPetRepositoryCustom{
	List<MemberPet> findByMember(Member member);
}

