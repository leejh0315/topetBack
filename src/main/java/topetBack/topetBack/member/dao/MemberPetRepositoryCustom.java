package topetBack.topetBack.member.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.domain.PetEntity;

@Repository
public interface MemberPetRepositoryCustom {

    List<Member> findMemberByPet(Long petId);
    List<PetEntity> findPetByMember(Long memberId);
}
