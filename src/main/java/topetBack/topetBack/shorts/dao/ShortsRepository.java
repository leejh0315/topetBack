package topetBack.topetBack.shorts.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import topetBack.topetBack.shorts.domain.ShortsEntity;

public interface ShortsRepository extends JpaRepository<ShortsEntity, Long>{

//	public ShortsEntity save(ShortsEntity shortsEntity);
	Optional<ShortsEntity> findById(Long id);
	@Query(value = "SELECT id FROM shorts ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Long findRandomShort();
	List<ShortsEntity> findByAuthorId(Long authorId);
}
