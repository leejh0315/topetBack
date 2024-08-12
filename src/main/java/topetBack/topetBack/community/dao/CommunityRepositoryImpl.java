package topetBack.topetBack.community.dao;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.QCommunityEntity;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import topetBack.topetBack.member.domain.QMember;

import java.util.ArrayList;
import java.util.List;

import static topetBack.topetBack.comment.domain.QCommentEntity.commentEntity;
import static topetBack.topetBack.community.domain.QCommunityEntity.communityEntity;

@Repository
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Autowired
    public CommunityRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<CommunityEntity> findAllByAnimalAndCategoryWithLikesSorted(String animal, String category, Predicate predicate, Pageable pageable) {


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

    @Override
    public Slice<CommunityEntity> findAllWithPredicate(Predicate predicate, Pageable pageable){

        List<Long> ids = queryFactory
                .select(communityEntity.id).from(communityEntity)
                .where(predicate)
                .orderBy(communityEntity.createdTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        boolean hasNext = ids.size() == pageable.getPageSize();

        if(ids.isEmpty()){
            return new SliceImpl<>(new ArrayList<>(), pageable, hasNext);
        }

        JPAQuery<CommunityEntity> query = queryFactory
                .select(communityEntity)
                .from(communityEntity)
                .leftJoin(communityEntity.author).fetchJoin()
                .leftJoin(communityEntity.fileGroupEntity).fetchJoin()
                .leftJoin(communityEntity.fileGroupEntity.fileList).fetchJoin()
                .where(communityEntity.id.in(ids));

        List<CommunityEntity> content = query.fetch();

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
