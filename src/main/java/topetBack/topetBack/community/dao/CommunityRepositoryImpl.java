package topetBack.topetBack.community.dao;

import static topetBack.topetBack.community.domain.QCommunityEntity.communityEntity;
import static topetBack.topetBack.likes.domain.QLikes.likes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import topetBack.topetBack.community.domain.CommunityEntity;

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
    public Slice<CommunityEntity> findAllWithPredicate(Predicate predicate, Pageable pageable, String orderby, String animal, String category) {
    	
    	
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
                .where(communityEntity.animal.eq(animal).and(communityEntity.category.eq(category)))
                .leftJoin(communityEntity.author).fetchJoin()
                
                .leftJoin(communityEntity.fileGroupEntity).fetchJoin()
                .leftJoin(communityEntity.fileGroupEntity.fileList).fetchJoin()
                
                .leftJoin(communityEntity.tagMappings).fetchJoin() // TagMapping 엔티티 조인
                .leftJoin(communityEntity.tagMappings.hashTag).fetchJoin() // HashTagEntity 조인
                .where(communityEntity.id.in(ids));

        // 동일한 정렬 기준을 적용하여 CommunityEntity를 가져옴
        if ("likes".equalsIgnoreCase(orderby)) {
            query.orderBy(communityEntity.likesList.size().desc());
        } else {
            query.orderBy(communityEntity.createdTime.desc());
        }

        List<CommunityEntity> content = query.fetch();
        System.out.println(content);
        return new SliceImpl<>(content, pageable, hasNext);
    }
    
    
    @Override
    public Slice<CommunityEntity> findByAuthorId(Pageable pageable, Long id){
    	List<CommunityEntity> content = queryFactory
                .select(communityEntity)
                .from(communityEntity)
                .leftJoin(communityEntity.fileGroupEntity).fetchJoin()
                .leftJoin(communityEntity.fileGroupEntity.fileList).fetchJoin()
                .where(communityEntity.author.id.eq(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    	
    	boolean hasNext = content.size() == pageable.getPageSize();
    	
    	return new SliceImpl<>(content, pageable, hasNext);
    }
    

	@Override
	public Slice<CommunityEntity> getLikedCommunityByAuthorId(Pageable pageable, Long authorId) {
		
		List<Long> subQuery = queryFactory.select(likes.community.id).from(likes).where(likes.author.id.eq(authorId)).fetch();
		System.out.println(subQuery);
		List<CommunityEntity> query = queryFactory
				.select(communityEntity)
				.from(communityEntity)
				.where(communityEntity.id.in(subQuery)).fetch();
		boolean hasNext = query.size() == pageable.getPageSize();
		return new SliceImpl<>(query, pageable, hasNext);
	}

	@Override
	public List<CommunityEntity> bestCommunity() {

	        // 최근 한 달 간의 날짜 계산
	        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

	        // 최근 한 달 간의 게시글 중에서 좋아요 수를 집계하고, 내림차순으로 정렬하여 상위 5개를 가져오는 쿼리
	        List<CommunityEntity> topLikedCommunities = queryFactory
	            .selectFrom(communityEntity)
	            .leftJoin(likes).on(likes.community.id.eq(communityEntity.id))
	            .where(communityEntity.createdTime.after(oneMonthAgo))
	            .groupBy(communityEntity.id)
	            .orderBy(likes.count().desc())
	            .limit(5)
	            .fetch(); 

	        return topLikedCommunities;
			}

}
