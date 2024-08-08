package topetBack.topetBack.comment.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import topetBack.topetBack.comment.domain.CommentResponseDTO;
import topetBack.topetBack.comment.domain.QCommentEntity;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunitySummaryResponseDTO;
import topetBack.topetBack.community.domain.QCommunityEntity;
import topetBack.topetBack.member.domain.MemberSummaryResponseDTO;
import topetBack.topetBack.member.domain.QMember;

import static topetBack.topetBack.comment.domain.QCommentEntity.commentEntity;

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

        JPAQuery<CommentEntity> query = queryFactory.selectFrom(commentEntity)
                .leftJoin(commentEntity.community).fetchJoin()
                .where(commentEntity.author.id.eq(id).and(commentEntity.deleted.isFalse()))
                .orderBy(commentEntity.createdTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<CommentEntity> content = query.fetch();
        boolean hasNext = content.size() == pageable.getPageSize();

        return new SliceImpl<>(content, pageable, hasNext);
    }

    
    @Override
    public void updateComment(CommentEntity comment) {
        queryFactory.update(commentEntity)
                .where(commentEntity.id.eq(comment.getId())) // 댓글 ID로 조건 설정
                .set(commentEntity.content, comment.getContent()) // 댓글 내용 업데이트
                .execute();
    }

    
}
