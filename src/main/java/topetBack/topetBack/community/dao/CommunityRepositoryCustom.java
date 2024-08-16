package topetBack.topetBack.community.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;

import topetBack.topetBack.community.domain.CommunityEntity;

@Repository
public interface CommunityRepositoryCustom {
//	Slice<CommunityEntity> findAllByAnimalAndCategoryWithLikesSorted(String animal, String category, Predicate predicate, Pageable pageable);
	Slice<CommunityEntity> findAllWithPredicate(Predicate predicate, Pageable pageable, String orderby, String animal, String category);
	Slice<CommunityEntity> findByAuthorId(Pageable pageable,Long id);//사용자에 맞는 게시글 가져오기
}
