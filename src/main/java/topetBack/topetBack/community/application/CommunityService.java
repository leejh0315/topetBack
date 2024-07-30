package topetBack.topetBack.community.application;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.member.domain.Member;


@Service
public interface  CommunityService {

	List<CommunityResponseDTO> getCommunityListByCategory(String category);

	CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO) throws IOException;

	CommunityResponseDTO getCommunityById(long communityId);

	public List<CommunityResponseDTO> getCommunityListByAnimalAndCategory(String animal, String category, int page, int size);
	
	public CommunityResponseDTO updateCommunity(Long id, CommunityRequestDTO communityRequestDTO) throws NotFoundException;
	
	List<CommunityResponseDTO> findByAuthorId (Long id);	//사용자에맞는게시글가져오기
	
	void deleteCommunity(Long id) throws NotFoundException;
	
	public List<CommunityResponseDTO> getCommunityListByAnimalAndLike(String animal);
	
	List<CommunityResponseDTO> getCommunityListByAnimalAndCategoryAndLike(String animal , String category , int page , int size);
}
