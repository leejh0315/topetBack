package topetBack.topetBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import topetBack.topetBack.domain.Community;
import topetBack.topetBack.form.CommunityPostForm;


public interface ToPetCommunityRepository extends JpaRepository<CommunityPostForm , Long>{
	
}
