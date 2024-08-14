package topetBack.topetBack.block.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import topetBack.topetBack.block.domain.BlockEntity;
import topetBack.topetBack.member.domain.Member;

public interface BlockRepository extends JpaRepository<BlockEntity , Long>{
	boolean existsByBlockerAndBlocked(Member blocker , Member blocked);
	void deleteByBlockerAndBlocked(Member blocker , Member blocked);
	Optional<BlockEntity> findById(Long id);
	
	@Query("SELECT b.blocked.id FROM BlockEntity b WHERE b.blocker.id = :blockerId")
    List<Long> findBlockedUserIdsByBlocker(@Param("blockerId") Long blockerId);
}
