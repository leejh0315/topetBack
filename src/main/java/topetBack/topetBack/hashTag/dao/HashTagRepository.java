package topetBack.topetBack.hashTag.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import topetBack.topetBack.hashTag.domain.HashTagEntity;
import topetBack.topetBack.hashTag.domain.TagMapping;


public interface HashTagRepository extends JpaRepository<HashTagEntity, Long>{
	HashTagEntity findByTag(String tag);
	
	@Modifying
    @Query("DELETE FROM HashTagEntity h WHERE h.tagMapping= :tagMapping")
	void deleteByTagMappingId(@Param("tagMapping") TagMapping tagMapping);
}
