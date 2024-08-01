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

        // 자식 댓글을 부모 댓글의 children 리스트에 추가
        for (CommentEntity entity : commentEntities) {
            CommentResponseDTO dto = commentMap.get(entity.getId());
            if (dto.getParentId() != null && commentMap.containsKey(dto.getParentId())) {
                CommentResponseDTO parentDto = commentMap.get(dto.getParentId());

                // 중복 추가 방지: 자식 댓글이 이미 추가되어 있는지 확인
                boolean alreadyExists = parentDto.getChildren().stream()
                    .anyMatch(child -> child.getId().equals(dto.getId()));

                if (!alreadyExists) {
                    parentDto.getChildren().add(dto);
                }
            }
        }

        // 최상위 댓글만 반환, 중복 카운트 방지
        return commentMap.values().stream()
                .filter(dto -> dto.getParentId() == null) // 부모 댓글이 없는 최상위 댓글만 반환
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
