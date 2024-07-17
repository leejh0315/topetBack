package topetBack.topetBack.community;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.config.SessionVar;
import topetBack.topetBack.member.domain.Member;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class CommunityController {
    

	private final CommunityService communityService;

    @PostMapping("/communityPost")
    public ResponseEntity<CommunityResponseDTO> communityPost(@ModelAttribute CommunityRequestDTO communityRequestDTO, HttpServletRequest req) throws Exception  {
    	
    	HttpSession session = req.getSession(false);
		Member member = (Member) session.getAttribute(SessionVar.LOGIN_MEMBER);
		
		System.out.println("DTO : " + communityRequestDTO);
		System.out.println("member :" + member);
		
		communityRequestDTO.setAuthor(member);
		
		System.out.println("communityPost 요청 등록됨");
		System.out.println(communityRequestDTO);

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
