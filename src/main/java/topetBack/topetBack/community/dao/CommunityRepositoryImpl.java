package topetBack.topetBack.community.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.QCommunityEntity;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Repository
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CommunityRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<CommunityEntity> findAllByAnimalAndCategoryWithLikesSorted(String animal, String category, Predicate predicate, Pageable pageable) {
        QCommunityEntity communityEntity = QCommunityEntity.communityEntity;

        BooleanExpression baseFilter = communityEntity.animal.eq(animal)
                .and(communityEntity.category.eq(category));

        Predicate combinedPredicate = baseFilter.and(predicate);

        PageRequest pageRequest = (PageRequest) pageable;

        return queryFactory.selectFrom(communityEntity)
                .where(combinedPredicate)
                .orderBy(communityEntity.likesList.size().desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }
}
