package topetBack.topetBack.community;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.domain.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {


	private final CommunityService communityService;
	private final SessionManager sessionManager;
	
	@Transactional
    @PostMapping("/post")
    public 
    String communityPost(@ModelAttribute CommunityRequestDTO communityRequestDTO, 
    		HttpServletRequest req) throws Exception  {
    	
    	Member sessionMember = sessionManager.getSessionObject(req).toMember();
		communityRequestDTO.setAuthor(sessionMember);
		CommunityResponseDTO communityResponseDTO = communityService.createCommunity(communityRequestDTO);


		return ResponseEntity.ok(communityResponseDTO).toString();
	}

	// 게시판 리스트
    @GetMapping("/{animal}/{category}")
    public Object[] boardList(Model model, @PathVariable("animal") String animal, 
                              @PathVariable("category") String category,
                              @RequestParam(name = "page") int page, 
                              @RequestParam(name = "size") int size) {

        if ("freedomAndDaily".equals(category)) {
            category = "자유/일상";
        } else if ("curious".equals(category)) {
            category = "궁금해요";
        } else {
            category = "정보공유";
        }
        
        System.out.println("페이지 : " + page + "사이즈 : "+ size);
        return communityService.getCommunityListByAnimalAndCategory(animal, category, page, size).toArray();
    }

    //게시판 디테일
    @GetMapping("/detail/{id}")
    public CommunityResponseDTO communityDetail(Model model ,  @PathVariable("id") int id)
    {        

    	return communityService.getCommunityById(id);

    }

    //게시물 삭제
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable("id") Long id) throws NotFoundException {
        communityService.deleteCommunity(id);

        return ResponseEntity.noContent().build() ;	
    }
    
    @PostMapping("/update/{id}")			
    public ResponseEntity<CommunityResponseDTO> updateCommunity(
            @PathVariable("id") Long id,
            @RequestBody CommunityRequestDTO communityRequestDTO) throws NotFoundException {
        CommunityResponseDTO updatedCommunity = communityService.updateCommunity(id, communityRequestDTO);
        return ResponseEntity.ok(updatedCommunity);
    }
    
    //사용자에 맞는 게시글 가져오기
    @GetMapping("/myCommunity/{id}")
    public List<CommunityResponseDTO> getMyCommunity(@PathVariable("id") Long id){
    	return communityService.findByAuthorId(id);
    }
    
    
    //인기순
    @GetMapping("/{animal}/{category}/sortLike")
    public Object[] boardLikeList(Model model, @PathVariable("animal")String animal, @PathVariable("category")String category){
    	
    	if ("freedomAndDaily".equals(category)) {
            category = "자유/일상";
        } else if ("curious".equals(category)) {
            category = "궁금해요";
        } else {	
            category = "정보공유";
        }
    	
		return communityService.getCommunityListByAnimalAndCategoryAndLike(animal, category).toArray();
    	
    }
    
  //동물통합 인기순
    @GetMapping("/{animal}/sortLike")
    public Object[] boardLikeAnimalList(Model model, @PathVariable("animal")String animal){
    	
		return communityService.getCommunityListByAnimalAndLike(animal).toArray();
    	
    }
}
