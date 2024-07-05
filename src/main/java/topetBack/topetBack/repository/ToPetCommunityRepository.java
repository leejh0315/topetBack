package topetBack.topetBack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import topetBack.topetBack.form.CommunityPostForm;


public interface ToPetCommunityRepository extends JpaRepository<CommunityPostForm , Long>{
//	List<CommunityPostForm> findByPost(int post_id);
}
