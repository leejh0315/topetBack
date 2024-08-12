package topetBack.topetBack.block.application;

import org.springframework.stereotype.Service;

import topetBack.topetBack.block.domain.BlockRequestDTO;

@Service
public interface BlockService {
	
	public void blockUser(BlockRequestDTO blockRequestDTO);
	public void unblockUser(BlockRequestDTO blockRequestDTO);
	public boolean isBlocked(Long blockerId, Long blockedId);
}
