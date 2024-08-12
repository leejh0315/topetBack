package topetBack.topetBack.community.application;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.community.domain.CommunityListResponseDTO;


@Service
public interface  CommunityService {

	CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO) throws IOException;

	CommunityResponseDTO getCommunityById(long communityId);

	public List<CommunityListResponseDTO> getCommunityListByAnimalAndCategory(String animal, String category, int page, int size, Predicate predicate, Long currentUserId, String orderby);
	
	CommunityResponseDTO updateCommunity(Long id, CommunityRequestDTO communityRequestDTO) throws NotFoundException;
	
	List<CommunityResponseDTO> findByAuthorId (Long id);	//사용자에맞는게시글가져오기
	
	void deleteCommunity(Long id) throws NotFoundException;
	
	List<CommunityResponseDTO> getCommunityListByAnimalAndLike(String animal);
		
	
}
