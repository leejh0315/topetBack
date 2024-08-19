package topetBack.topetBack.shorts.dao;

import static topetBack.topetBack.likes.domain.QLikes.likes;
import static topetBack.topetBack.shorts.domain.QShortsEntity.shortsEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import topetBack.topetBack.shorts.domain.ShortsEntity;

public class ShortsRepositoryCustomImpl implements ShortsRepositoryCustom{
	
    private final JPAQueryFactory queryFactory;


    @Autowired
    public ShortsRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

	@Override
	public Slice<ShortsEntity> getLikedShortsByAuthorId(Pageable pageable, Long authorId) {
		List<Long> subQuery = queryFactory.select(likes.shorts.id).from(likes).where(likes.author.id.eq(authorId)).fetch();
		List<ShortsEntity> query = queryFactory
				.select(shortsEntity)
				.from(shortsEntity)
				.where(shortsEntity.id.in(subQuery)).fetch();
		boolean hasNext = query.size() == pageable.getPageSize();
		return new SliceImpl<>(query, pageable, hasNext);
	}

}
