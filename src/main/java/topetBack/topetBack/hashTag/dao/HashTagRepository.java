package topetBack.topetBack.hashTag.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.hashTag.domain.HashTagEntity;

public interface HashTagRepository extends JpaRepository<HashTagEntity, Long>{
	HashTagEntity findByTag(String tag);
}
