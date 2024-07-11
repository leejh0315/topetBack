package topetBack.topetBack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.Dto.CommunityResponseDto;
import topetBack.topetBack.form.CommunityPostForm;
import topetBack.topetBack.repository.ToPetCommunityRepository;

@Service
@RequiredArgsConstructor
public class ToPetCommunityServicelmpl implements ToPetCommunityService{
	
private ToPetCommunityRepository toPetCommunityRepository;
    
    public ToPetCommunityServicelmpl(ToPetCommunityRepository toPetCommunityRepository) {
    	this.toPetCommunityRepository = toPetCommunityRepository;	
    }
    
    public List<CommunityResponseDto> findAll(){
    	List<CommunityPostForm> list = toPetCommunityRepository.findAll();
        return list.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
    }    
    
    public List<CommunityPostForm> getCommunityList(){
    	return toPetCommunityRepository.findAll();
    }
    
    @Override
    public List<CommunityPostForm> getCommunityPreviewByType(String animal , String category ){
		return toPetCommunityRepository.findByAnimalAndCategory(animal , category);
    }
    
    @Override
    public void deleteCommunity(Long post_id){
    	toPetCommunityRepository.deleteById(post_id);
    }
    
  
    
    
}