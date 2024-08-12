package topetBack.topetBack.comment.dao;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;

import static topetBack.topetBack.comment.domain.QCommentEntity.commentEntity;
import static topetBack.topetBack.community.domain.QCommunityEntity.communityEntity;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<CommentEntity> findByCommunityId(Long id, Pageable pageable) {

        List<Long> ids = queryFactory
                .select(commentEntity.id).from(commentEntity)
                .where(commentEntity.community.id.eq(id).and(commentEntity.deleted.isFalse()).and(commentEntity.parent.isNull()))
                .orderBy(commentEntity.createdTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        boolean hasNext = ids.size() == pageable.getPageSize();

        if(ids.isEmpty()){
            return new SliceImpl<>(new ArrayList<>(), pageable, hasNext);
        }

        List<CommentEntity> content = queryFactory.selectFrom(commentEntity)
                .leftJoin(commentEntity.author).fetchJoin()
                .leftJoin(commentEntity.children).fetchJoin()
                .where(commentEntity.id.in(ids))
                .orderBy(commentEntity.createdTime.desc())
                .fetch();

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<CommentEntity> findByAuthorId(Long id, Pageable pageable) {

        JPAQuery<CommentEntity> query = queryFactory
                .select(
                        Projections.fields(
                                CommentEntity.class,
                                commentEntity.id,
                                commentEntity.content,
                                commentEntity.createdTime,
                                commentEntity.updatedTime,
                                commentEntity.parent.id.as("parentId"),
                                Projections.fields(CommunityEntity.class,
                                        communityEntity.id,
                                        communityEntity.title
                                ).as("community")
                        )
                )
                .from(commentEntity)
                .leftJoin(commentEntity.community, communityEntity)
                .where(commentEntity.author.id.eq(id).and(commentEntity.deleted.isFalse()))
                .orderBy(commentEntity.createdTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<CommentEntity> content = query.fetch();
        boolean hasNext = content.size() == pageable.getPageSize();

        return new SliceImpl<>(content, pageable, hasNext);
    }

    
}
