package topetBack.topetBack.block.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.block.domain.BlockEntity;
import topetBack.topetBack.member.domain.Member;

public interface BlockRepository extends JpaRepository<BlockEntity , Long>{
	boolean existsByBlockerAndBlocked(Member blocker , Member blocked);
	void deleteByBlockerAndBlocked(Member blocker , Member blocked);
}
