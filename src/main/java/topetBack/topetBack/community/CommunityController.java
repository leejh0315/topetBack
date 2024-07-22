package topetBack.topetBack.community;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.member.domain.Member;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class CommunityController {


	private final CommunityService communityService;

    @PostMapping("/communityPost")
    public ResponseEntity<CommunityResponseDTO> communityPost(@ModelAttribute CommunityRequestDTO communityRequestDTO) throws Exception  {

//    	HttpSession session = req.getSession(false);
//		Member member = (Member) session.getAttribute(SessionVar.LOGIN_MEMBER);

		System.out.println("DTO : " + communityRequestDTO);
//		System.out.println("member :" + member);

//		communityRequestDTO.setAuthor(member);
		communityRequestDTO.setAuthor(new Member(1L, "test", "test","test"));

		System.out.println("communityPost 요청 등록됨");
		System.out.println(communityRequestDTO);

		CommunityResponseDTO communityResponseDTO = communityService.createCommunity(communityRequestDTO);


		return ResponseEntity.ok(communityResponseDTO);
	}

    //게시판 리스트
    @GetMapping("/{animal}/{category}")
    public Object[] boardList(Model model, @PathVariable("animal")String animal, @PathVariable("category")String category
    	) {

    	if ("freedomAndDaily".equals(category)) {
            category = "자유/일상";
        } else if ("curious".equals(category)) {
            category = "궁금해요";
        } else {
            category = "정보공유";
        }


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
    @PostMapping("/delete/{postId}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable("postId") Long postId) throws NotFoundException {
        communityService.deleteCommunity(postId);
        System.out.println("삭제 게시물 번호: " + postId);

        return ResponseEntity.noContent().build();	
    }
    
    @PostMapping("/update/{id}")			
    public ResponseEntity<CommunityResponseDTO> updateCommunity(
            @PathVariable("id") Long id,
            @RequestBody CommunityRequestDTO communityRequestDTO) throws NotFoundException {
        CommunityResponseDTO updatedCommunity = communityService.updateCommunity(id, communityRequestDTO);
        System.out.println("수정 출력 테스트" + communityRequestDTO);
        return ResponseEntity.ok(updatedCommunity);
    }

}
