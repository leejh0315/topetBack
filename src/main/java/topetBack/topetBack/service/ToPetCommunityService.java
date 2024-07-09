package topetBack.topetBack.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import topetBack.topetBack.Dto.CommunityResponseDto;
import topetBack.topetBack.form.CommunityPostForm;
import topetBack.topetBack.repository.ToPetCommunityRepository;


public interface  ToPetCommunityService {
	 public List<CommunityPostForm> getCommunityList();
	 public List<CommunityPostForm> getCommunityPreviewByType(String animal, String category);
	 public void deleteCommunity(Long id);
	 public List<CommunityResponseDto> findAll();
}
