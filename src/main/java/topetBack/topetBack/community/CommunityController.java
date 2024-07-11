package topetBack.topetBack.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.community.domain.CommunityVo;
import topetBack.topetBack.community.validation.CommunityVaildator;

@RestController
@RequiredArgsConstructor
@Slf4j
@Controller
public class CommunityController {
	
    private final CommunityVaildator vaildator;
    
    @Autowired
	private CommunityService toPetCommunityService;
	
    
	@InitBinder
	public void init(WebDataBinder  webDataBinder) {
		webDataBinder.addValidators(vaildator);
	}
	
	@PostMapping("/api/community/communityPost")
    public Long communityPost(
    		@RequestParam(value="photos", required=false) List<MultipartFile> photos,
    		@ModelAttribute CommunityVo communityVO
    	     ) throws Exception  {
		System.out.println("communityPost 요청 등록됨");
		System.out.println("photos" + photos);
		
		toPetCommunityService.create(communityVO, photos);
//		 System.out.println("Received data: " + title + ", " + content + ", " + category + ", " + hashtag + ", " + animal  + ", " + photos);
		 
//		CommunityVo requseVo = CommunityVo.builder()
//				.title(title)
//	            .content(content)
//	            .category(category)
//	            .hashtag(hashtag)
//	            .animal(animal)
//	            .build();
//		
        //return toPetCommunityService.create(requseVo, photos);
		return (long) 1;

	}
	
		
    //게시판 리스트
    @GetMapping("/api/community/{animal}/{category}")
    public String boardList(Model model, @PathVariable("animal")String animal, @PathVariable("category")String category
    	) {
    	System.out.println(animal + category);
    	System.out.println("test" + toPetCommunityService.getCommunityPreviewByType(animal, category).toString());
    	return animal + " " + category;
    }

    
    //게시물 삭제
    @GetMapping("/api/community/delete/{postId}")
    public String deleteCommunity(@PathVariable("postId") Long postId) {
        toPetCommunityService.deleteCommunity(postId);
        System.out.println("삭제 게시물 번호: " + postId);
        
        return "삭제 게시물 번호: " + postId;
    }
    
}
