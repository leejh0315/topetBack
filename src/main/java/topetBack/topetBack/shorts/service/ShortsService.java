package topetBack.topetBack.shorts.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import topetBack.topetBack.shorts.domain.ShortsRequestDTO;
import topetBack.topetBack.shorts.domain.ShortsResponseDTO;

@Service
public interface ShortsService {

	public ShortsResponseDTO shortsSave(ShortsRequestDTO shortsRequestDTO)throws IOException ; 
		
		
	
}
