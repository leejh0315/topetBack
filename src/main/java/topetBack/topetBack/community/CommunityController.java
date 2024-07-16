package topetBack.topetBack.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.community.validation.CommunityVaildator;
import topetBack.topetBack.member.domain.Member;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class CommunityController {
    

	private final CommunityService communityService;

    @PostMapping("/communityPost")
    public ResponseEntity<CommunityResponseDTO> communityPost(@ModelAttribute CommunityRequestDTO communityRequestDTO) throws Exception  {
		System.out.println("communityPost 요청 등록됨");
		System.out.println(communityRequestDTO);
		//TODO
		communityRequestDTO.setAuthor(new Member(1, "test", "test","test"));

		CommunityResponseDTO communityResponseDTO = communityService.createCommunity(communityRequestDTO);

		return ResponseEntity.ok(communityResponseDTO);
	}
		
    //게시판 리스트
    @GetMapping("/{animal}/{category}")
    public Object[] boardList(Model model, @PathVariable("animal")String animal, @PathVariable("category")String category
    	) {
    	System.out.println(animal + category);
    	System.out.println("test" + communityService.getCommunityListByCategory(category).toString());
    	System.out.println("동물 + 카테고리 : " + " " + communityService.getCommunityListByAnimalAndCategory(animal, category));
    	return communityService.getCommunityListByAnimalAndCategory(animal, category).toArray();
    }
    
    //게시판 디테일
    @GetMapping("/detail/{id}")
    public CommunityResponseDTO communityDetail(Model model ,  @PathVariable("id") int id)
    {
    	System.out.println("게시물 ID : " + id);
    	
    	return communityService.getCommunityById(id);
    	
    }
    
    
    

    
    //게시물 삭제
    @GetMapping("/delete/{postId}")
    public String deleteCommunity(@PathVariable("postId") Long postId) {
        communityService.deleteCommunity(postId);
        System.out.println("삭제 게시물 번호: " + postId);
        
        return "삭제 게시물 번호: " + postId;
    }
    
}
