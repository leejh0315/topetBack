package topetBack.topetBack.member.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.MemberPet;
import topetBack.topetBack.member.domain.QMember;
import topetBack.topetBack.member.domain.QMemberPet;
import topetBack.topetBack.pet.domain.PetEntity;

import java.util.List;

public class MemberPetRepositoryCustomImpl implements MemberPetRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberPetRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Member> findMemberByPet(Long petId){
        QMemberPet memberPet = QMemberPet.memberPet;
        QMember member = QMember.member;

        return jpaQueryFactory
                .select(member)
                .from(memberPet)
                .join(memberPet.member, member)
                .where(memberPet.pet.id.eq(petId))
                .fetch();
    }
}
