package topetBack.topetBack.shorts.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.shorts.domain.ShortsEntity;

public interface ShortsRepository extends JpaRepository<ShortsEntity, Long>{

//	public ShortsEntity save(ShortsEntity shortsEntity);
}
