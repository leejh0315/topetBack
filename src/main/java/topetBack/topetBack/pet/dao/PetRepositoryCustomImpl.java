package topetBack.topetBack.pet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import topetBack.topetBack.pet.domain.QPetEntity;

@Repository
public class PetRepositoryCustomImpl implements PetRepositoryCustom{

	private final JPAQueryFactory queryFactory;


    @Autowired
    public PetRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
	@Override
	public Long deleteByPetId(Long id) {
		System.out.println("custom");
		QPetEntity petEntity= QPetEntity.petEntity;
		Long deleteQuery = queryFactory
				.delete(petEntity)
				.where(petEntity.id.eq(id)).execute();
		System.out.println(deleteQuery);
		return deleteQuery;
	}

}
