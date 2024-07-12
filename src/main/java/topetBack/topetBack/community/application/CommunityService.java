package topetBack.topetBack.community.application;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import topetBack.topetBack.community.domain.CommunityDomain;
import topetBack.topetBack.community.domain.CommunityVo;


public interface  CommunityService {
	 public List<CommunityDomain> getCommunityList();
	 public List<CommunityDomain> getCommunityPreviewByType(String animal, String category);
	 public void deleteCommunity(Long id);
	 public Long create(CommunityVo requstVo , List<MultipartFile> files) throws Exception;

}
