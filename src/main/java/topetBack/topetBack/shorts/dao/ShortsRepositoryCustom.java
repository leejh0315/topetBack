package topetBack.topetBack.shorts.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.shorts.domain.ShortsEntity;

@Repository
public interface ShortsRepositoryCustom {
	Slice<ShortsEntity> getLikedShortsByAuthorId(Pageable pageable, Long authorId);
}
