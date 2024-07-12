package topetBack.topetBack.community.application;

import java.io.IOException;
import java.io.IOException;
import java.util.List;

import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;



public interface  CommunityService {

	List<CommunityResponseDTO> getCommunityListByCategory(String category);

	CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO) throws IOException;

	CommunityResponseDTO getCommunityById(long communityId);

	void deleteCommunity(Long id);
}
