package topetBack.topetBack.block.application;

import java.util.List;

import org.springframework.stereotype.Service;

import topetBack.topetBack.block.domain.BlockRequestDTO;
import topetBack.topetBack.block.domain.BlockResponseDTO;

@Service
public interface BlockService {
	
	public void blockUser(BlockRequestDTO blockRequestDTO);
	public void unblockUser(BlockRequestDTO blockRequestDTO);
	public boolean isBlocked(Long blockerId, Long blockedId);
	List<BlockResponseDTO> findByAuthorId(Long id);
}
