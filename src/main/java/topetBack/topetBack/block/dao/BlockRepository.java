package topetBack.topetBack.block.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;
import topetBack.topetBack.block.domain.BlockEntity;
import topetBack.topetBack.member.domain.Member;

public interface BlockRepository extends JpaRepository<BlockEntity , Long>{
	boolean existsByBlockerAndBlocked(Member blocker , Member blocked);
	void deleteByBlockerAndBlocked(Member blocker , Member blocked);
	
	@Query("SELECT b.blocked.id FROM BlockEntity b WHERE b.blocker.id = :blockerId")
    List<Long> findBlockedUserIdsByBlocker(@Param("blockerId") Long blockerId);
}
