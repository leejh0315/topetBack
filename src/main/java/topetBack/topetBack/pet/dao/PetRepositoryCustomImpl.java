package topetBack.topetBack.pet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import topetBack.topetBack.member.domain.QMemberPet;
import topetBack.topetBack.pet.domain.QPetEntity;

@Repository
public class PetRepositoryCustomImpl implements PetRepositoryCustom{

	private final JPAQueryFactory queryFactory;


    @Autowired
    public PetRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
	@Override
	public Long deleteByPetIdCustom(Long memberId, Long petId) {
		
		QMemberPet memberPet = QMemberPet.memberPet;
		Long deleteMemberPet = queryFactory
				.delete(memberPet)
				.where(memberPet.member.id.eq(memberId).and(memberPet.pet.id.eq(petId))).execute();
				
		
		System.out.println("custom");
		QPetEntity petEntity= QPetEntity.petEntity;
		Long deleteQuery = queryFactory
				.delete(petEntity)
				.where(petEntity.id.eq(petId)).execute();
		System.out.println(deleteQuery);
		return deleteQuery;
	}

}
