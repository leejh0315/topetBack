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

//    public Slice<CommunityEntity> findAllByAnimalAndCategoryWithLikesSorted(Predicate predicate, Pageable pageable) {
//
//
//        PageRequest pageRequest = (PageRequest) pageable;
//
//        return queryFactory.selectFrom(communityEntity)
//                .where(predicate)
//                .orderBy(communityEntity.likesList.size().desc())
//                .offset(pageRequest.getOffset())
//                .limit(pageRequest.getPageSize())
//                .fetch();
//    }
    
   

    @Override
    public Slice<CommunityEntity> findAllWithPredicate(Predicate predicate, Pageable pageable, String orderby) {
        JPAQuery<Long> idQuery = queryFactory
                .select(communityEntity.id)
                .from(communityEntity)
                .where(predicate);

        // 정렬 기준에 따라 다르게 처리
        if ("likes".equalsIgnoreCase(orderby)) {
            idQuery.orderBy(communityEntity.likesList.size().desc());
        } else {
            idQuery.orderBy(communityEntity.createdTime.desc());
        }

        List<Long> ids = idQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        boolean hasNext = ids.size() == pageable.getPageSize();

        if (ids.isEmpty()) {
            return new SliceImpl<>(new ArrayList<>(), pageable, hasNext);
        }

        JPAQuery<CommunityEntity> query = queryFactory
                .select(communityEntity)
                .from(communityEntity)
                .leftJoin(communityEntity.author).fetchJoin()
                .leftJoin(communityEntity.fileGroupEntity).fetchJoin()
                .leftJoin(communityEntity.fileGroupEntity.fileList).fetchJoin()
                .where(communityEntity.id.in(ids));

        // 동일한 정렬 기준을 적용하여 CommunityEntity를 가져옴
        if ("likes".equalsIgnoreCase(orderby)) {
            query.orderBy(communityEntity.likesList.size().desc());
        } else {
            query.orderBy(communityEntity.createdTime.desc());
        }

        List<CommunityEntity> content = query.fetch();

        return new SliceImpl<>(content, pageable, hasNext);
    }

}
