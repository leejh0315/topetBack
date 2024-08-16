package topetBack.topetBack.member.dao;

import java.util.List;
import org.hibernate.mapping.SoftDeletable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.QMember;
import topetBack.topetBack.member.domain.QMemberPet;
import topetBack.topetBack.pet.domain.PetEntity;
import topetBack.topetBack.pet.domain.QPetEntity;


public class MemberPetRepositoryCustomImpl implements MemberPetRepositoryCustom {

	@Autowired
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

	@Override
	public List<PetEntity> findPetByMember(Long memberId) {
		QMemberPet memberPet = QMemberPet.memberPet;
		QPetEntity petEntity = QPetEntity.petEntity;
		return jpaQueryFactory
				.select(petEntity)
				.from(memberPet)
				.join(memberPet.pet, petEntity)
				.where(memberPet.member.id.eq(memberId))
				.fetch();
	}

	@Override
	public Long deleteMember(Long memberId , Long petId) {
		// TODO Auto-generated method stub
		
		QMemberPet memberPet = QMemberPet.memberPet;
		Long deleteQuery = jpaQueryFactory
				.delete(memberPet)
				.where(memberPet.member.id.eq(memberId).and(memberPet.pet.id.eq(petId))).execute();
		System.out.println(deleteQuery);
		return deleteQuery;
	}
}
