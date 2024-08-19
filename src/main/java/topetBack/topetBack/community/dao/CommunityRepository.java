package topetBack.topetBack.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.QCommunityEntity;
import topetBack.topetBack.member.domain.Member;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> , QuerydslPredicateExecutor<CommunityEntity> ,QuerydslBinderCustomizer<QCommunityEntity> , CommunityRepositoryCustom{


	List<CommunityEntity> findByAnimalAndCategory(String animal, String category);
    Slice<CommunityEntity> findByAnimalAndCategoryOrderByCreatedTimeDesc(String animal, String category, Pageable pageable);

	List<CommunityEntity> findByCategory(String category);

	@Override
	Optional<CommunityEntity> findById(Long communityId);
	
	
    @Query("SELECT c FROM CommunityEntity c LEFT JOIN c.likesList l WHERE c.animal = :animal AND c.category = :category GROUP BY c ORDER BY COUNT(l) DESC")
    Slice<CommunityEntity> findAllOrderByLikesCountDesc(@Param("animal") String animal, @Param("category") String category , Pageable pageable);
    
    @Query("SELECT c FROM CommunityEntity c LEFT JOIN c.likesList l WHERE c.animal = :animal GROUP BY c ORDER BY COUNT(l) DESC")
    List<CommunityEntity> findAnimalOrderByLikesCountDesc(@Param("animal") String animal);
    
    
    
    @Override
    default void customize(QuerydslBindings bindings, QCommunityEntity root){
        bindings.excludeUnlistedProperties(true);
        // 현재 QuerydslPredicateExecutor에 의해서 엔티티에 있는 모든 필드에 대한 검색이 되고 있다. 우리가 원하는 필드만 설정하기 위해서 사용한다.
        // true로 하면 including 하지 않은 필드는 검색에서 제외한다. (기본값은 false)
        
//        String tag = root.tagMappings.hashTag.any().tag.toString();
        bindings.including(root.title, root.content, root.animal, root.category, root.tag);
//        		, root.hashTag); // 검색할 필드
        
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like ''
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '%%'
        // % 넣는 것을 수동으로 정하고 싶을 때에는 like를 사용하고, 그렇지 않을 경우에는 contains를 사용
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.tag).first(StringExpression::containsIgnoreCase);

//        bindings.bind(root.tagMappings.hashTag.get).first(StringExpression::containsIgnoreCase);

    }
}
