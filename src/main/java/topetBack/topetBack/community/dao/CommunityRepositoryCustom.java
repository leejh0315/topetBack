package topetBack.topetBack.community.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;

import topetBack.topetBack.community.domain.CommunityEntity;

@Repository
public interface CommunityRepositoryCustom {
	List<CommunityEntity> findAllByAnimalAndCategoryWithLikesSorted(String animal, String category, Predicate predicate, Pageable pageable);
	Slice<CommunityEntity> findAllWithPredicate(Predicate predicate, Pageable pageable);
}
