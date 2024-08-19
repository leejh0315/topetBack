package topetBack.topetBack.shorts.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import topetBack.topetBack.shorts.domain.ShortsRequestDTO;
import topetBack.topetBack.shorts.domain.ShortsResponseDTO;
import topetBack.topetBack.shorts.domain.ShortsSummaryResponseDTO;

@Service
public interface ShortsService {

	public ShortsResponseDTO shortsSave(ShortsRequestDTO shortsRequestDTO)throws IOException ; 
	
	
	List<ShortsResponseDTO> getMyShorts(Long authorId);
//	public List<ShortsResponseDTO> getAll(int page, int size);
	List<ShortsResponseDTO> getAll();
	ShortsResponseDTO getShortsDetail(Long id);	
	Long getRandomShorts();
	List<ShortsSummaryResponseDTO> getFive();
	List<ShortsSummaryResponseDTO> getLikedShortsByAuthorId(Long id, int page, int size);
	
	
}
