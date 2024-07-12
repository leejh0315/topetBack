package topetBack.topetBack.community.application;

import java.io.IOException;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;


public interface  CommunityService {

	List<CommunityResponseDTO> getCommunityListByCategory(String category);

	CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO) throws IOException;

	CommunityResponseDTO getCommunityById(long communityId);

	void deleteCommunity(Long id);
}
