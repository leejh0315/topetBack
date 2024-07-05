package topetBack.topetBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.domain.Community;
import topetBack.topetBack.domain.Test;

public interface ToPetCommunityRepository extends JpaRepository<Community , Long>{
	
}
