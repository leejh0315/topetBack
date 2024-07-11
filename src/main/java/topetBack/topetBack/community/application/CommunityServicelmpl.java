package topetBack.topetBack.community.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityDomain;
import topetBack.topetBack.community.domain.CommunityVo;
import topetBack.topetBack.file.application.FileHandler;
import topetBack.topetBack.file.dao.ImageRepository;
import topetBack.topetBack.file.domain.Image;

@Service
public class CommunityServicelmpl implements CommunityService{
	
private CommunityRepository communityRepository;
private ImageRepository imageRepository;
    
    public CommunityServicelmpl(CommunityRepository communityRepository , ImageRepository imageRepository) {
    	this.communityRepository = communityRepository;	
    	this.imageRepository = imageRepository;
    }
    
    @Transactional
    public Long create(CommunityVo requstVo, List<MultipartFile> files) {
    	CommunityDomain communityDomain = new CommunityDomain(
    			requstVo.getTitle(),
    			requstVo.getContent(),
    			requstVo.getHashtag(),
    			requstVo.getCategory(),
    			requstVo.getAnimal()
    			);
        
    	List<Image> photoList = null;
		
    	try {
			photoList = FileHandler.parseFileInfo(files);
		      if(!photoList.isEmpty()) {
		            for(Image image : photoList) {
		                // 파일을 DB에 저장
		            	communityDomain.addPhoto(imageRepository.save(image));
		            }
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        // 파일이 존재할 때에만 처리
  

        return communityRepository.save(communityDomain).getId();
       
}
    
    public List<CommunityDomain> getCommunityList(){
    	return communityRepository.findAll();
    }
    
    @Override
    public List<CommunityDomain> getCommunityPreviewByType(String animal , String category ){
		return communityRepository.findByAnimalAndCategory(animal , category);
    }
    
    @Override
    public void deleteCommunity(Long post_id){
    	communityRepository.deleteById(post_id);
    }

	

	


	
	 

	
    
   
  
    
    
}