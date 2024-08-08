package topetBack.topetBack.comment.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.querydsl.core.types.Projections;
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

    private static final QCommentEntity comment = commentEntity;
    private static final QMember member = QMember.member;
    private static final QCommunityEntity community = QCommunityEntity.communityEntity;

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<CommentResponseDTO> findByCommunityId(Long id, Pageable pageable) {

        // 빠른 페이징을 위해 id만 따로 SELECT
        List<Long> ids = queryFactory
                .select(comment.id).from(comment)
                .where(comment.community.id.eq(id).and(comment.deleted.isFalse()).and(comment.parent.isNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        boolean hasNext = ids.size() == pageable.getPageSize();

        if(ids.isEmpty()){
            return new SliceImpl<>(new ArrayList<>(), pageable, hasNext);
        }

        List<CommentResponseDTO> parent = queryFactory.select(
                Projections.fields(CommentResponseDTO.class,
                        comment.id,
                        comment.content,
                        comment.createdTime,
                        comment.updatedTime,
                        Projections.fields(MemberSummaryResponseDTO.class,
                                member.id,
                                member.name,
                                member.email).as("author")
                    )
                )
                .from(comment)
                .leftJoin(comment.author, member)
                .where(comment.id.in(ids))
                .fetch();

        List<CommentResponseDTO> child = queryFactory.select(
                Projections.fields(CommentResponseDTO.class,
                        comment.id,
                        comment.content,
                        comment.createdTime,
                        comment.updatedTime,
                        comment.parent.id.as("parentId"),
                        Projections.fields(MemberSummaryResponseDTO.class,
                                member.id,
                                member.name,
                                member.email).as("author")
                    )
                )
                .from(comment)
                .leftJoin(comment.author, member)
                .where(comment.parent.id.in(ids))
                .orderBy(comment.createdTime.asc()).fetch();

        // 최신순을 원한다면 수정 필요
        Map<Long, CommentResponseDTO> parentMap = parent.stream()
                .collect(Collectors.toMap(CommentResponseDTO::getId, Function.identity()));

        for (CommentResponseDTO childComment : child) {
            Long parentId = childComment.getParentId();
            if (parentMap.containsKey(parentId)) {
                CommentResponseDTO parentComment = parentMap.get(parentId);
                parentComment.getChildren().add(childComment); // 부모 댓글에 자식 댓글 추가
            }
        }

        List<CommentResponseDTO> content = new ArrayList<>(parentMap.values());

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<CommentResponseDTO> findByAuthorId(Long id, Pageable pageable) {

        // 빠른 페이징을 위해 id만 따로 SELECT
        List<Long> ids = queryFactory
                .select(comment.id).from(comment)
                .where(comment.author.id.eq(id).and(comment.deleted.isFalse()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        boolean hasNext = ids.size() == pageable.getPageSize();

        if(ids.isEmpty()){
            return new SliceImpl<>(new ArrayList<>(), pageable, hasNext);
        }

        List<CommentResponseDTO> content = queryFactory.select(
                Projections.fields(
                        CommentResponseDTO.class,
                        comment.id,
                        comment.content,
                        comment.createdTime,
                        comment.updatedTime,
                        comment.parent.id.as("parentId"),
                        Projections.fields(MemberSummaryResponseDTO.class,
                                member.id,
                                member.name,
                                member.email).as("author"),
                        Projections.fields(CommunitySummaryResponseDTO.class,
                                community.id,
                                community.title).as("community")
                    )
                )
                .from(comment)
                .leftJoin(comment.author, member)
                .leftJoin(comment.community, community)
                .where(comment.id.in(ids))
                .orderBy(comment.createdTime.desc()).fetch();

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
