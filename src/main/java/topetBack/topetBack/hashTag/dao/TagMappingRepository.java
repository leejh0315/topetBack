package topetBack.topetBack.hashTag.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.hashTag.domain.TagMapping;

@Repository
public interface TagMappingRepository extends JpaRepository<TagMapping, Long>{

}
