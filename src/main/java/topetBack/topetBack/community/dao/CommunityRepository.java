package topetBack.topetBack.community.dao;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.community.domain.CommunityDomain;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityDomain , Long> , JpaSpecificationExecutor<CommunityDomain>{
	

	List<CommunityDomain> findByAnimalAndCategory(String animal , String category);
	
	
	
}
