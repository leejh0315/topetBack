package topetBack.topetBack.comment.dao;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
    public List<CommentResponseDTO> findByCommunityId(Long id) {
        List<CommentEntity> commentEntities = queryFactory.selectFrom(commentEntity)
                .leftJoin(commentEntity.parent).fetchJoin()
                .where(commentEntity.community.id.eq(id)
                        .and(commentEntity.deleted.isFalse()))
                .orderBy(commentEntity.parent.id.asc().nullsFirst(), commentEntity.createdTime.asc())
                .fetch();

        // 트리구조로 변환
        Map<Long, CommentResponseDTO> commentMap = new HashMap<>();
        for (CommentEntity entity : commentEntities) {
            CommentResponseDTO dto = entity.toResponseDTO();
            commentMap.put(dto.getId(), dto);
        }

        for (CommentEntity entity : commentEntities) {
            CommentResponseDTO dto = commentMap.get(entity.getId());
            if (dto.getParentId() != null && commentMap.containsKey(dto.getParentId())) {
                CommentResponseDTO parentDto = commentMap.get(dto.getParentId());
                parentDto.getChildren().add(dto);
            }
        }

        // 최상위 댓글만 반환
        return commentMap.values().stream()
                .filter(dto -> dto.getParentId() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Slice<CommentResponseDTO> findByAuthorId(Long id, Pageable pageable) {
        QCommentEntity c = QCommentEntity.commentEntity;
        QMember m = QMember.member;
        QCommunityEntity p = QCommunityEntity.communityEntity;
        JPAQuery<CommentResponseDTO> query = queryFactory
                .select(
                        Projections.bean(CommentResponseDTO.class,
                                c.id,
                                c.content,
                                c.createdTime,
                                c.updatedTime,
                                c.parent.id.as("parentId"),
                                Projections.bean(MemberSummaryResponseDTO.class,
                                        m.id,
                                        m.name,
                                        m.email).as("author"),
                                Projections.bean(CommunitySummaryResponseDTO.class,
                                        p.id,
                                        p.title).as("community")
                        ))
                .from(c)
                .leftJoin(c.author, m)
                .leftJoin(c.community, p)
                .where(c.author.id.eq(id).and(c.deleted.isFalse()))
                .orderBy(c.createdTime.desc());

                // 페이지네이션 적용
                List<CommentResponseDTO> content = query
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

                // 총 개수 가져오기(댓글 테이블만 보면 되기에 따로 leftJoin 하지 않음)
                Long count = queryFactory
                        .select(c.count())
                        .from(c)
                        .where(c.author.id.eq(id).and(c.deleted.isFalse()))
                        .fetchOne();

                boolean hasNext = count > pageable.getOffset() + pageable.getPageSize();

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
