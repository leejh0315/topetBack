package topetBack.topetBack.comment.dao;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.comment.domain.CommentEntity;
import topetBack.topetBack.comment.domain.CommentResponseDTO;

import static topetBack.topetBack.comment.domain.QCommentEntity.commentEntity;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentResponseDTO> findByCommunityId(Long id) {
        List<CommentEntity> commentEntities = queryFactory.selectFrom(commentEntity)
                .leftJoin(commentEntity.parent)
                .fetchJoin()
                .where(commentEntity.community.id.eq(id)
                        .and(commentEntity.deleted.isFalse())) //논리 삭제 여부 나중에 물리 삭제로 변환?
                .orderBy(commentEntity.parent.id.asc().nullsFirst(), commentEntity.createdTime.asc())
                .fetch();

        //트리구조로변환
        Map<Long, CommentResponseDTO> commentMap = new HashMap<>();
        for (CommentEntity commentEntity : commentEntities) {
            CommentResponseDTO dto = commentEntity.toResponseDTO();
            commentMap.put(dto.getId(), dto);
        }

        for (CommentEntity commentEntity : commentEntities) {
            CommentResponseDTO dto = commentMap.get(commentEntity.getId());
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
    public void updateComment(CommentEntity comment) {
        queryFactory.update(commentEntity)
                .where(commentEntity.id.eq(comment.getId())) // 댓글 ID로 조건 설정
                .set(commentEntity.content, comment.getContent()) // 댓글 내용 업데이트
                .execute();
    }
    
}
