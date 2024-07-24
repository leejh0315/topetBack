package topetBack.topetBack.member.dao;

import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.pet.domain.PetEntity;

import java.util.List;

public interface MemberPetRepositoryCustom {

    List<Member> findMemberByPet(Long petId);
}
