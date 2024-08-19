package topetBack.topetBack.community;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.querydsl.core.types.Predicate;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import topetBack.topetBack.community.application.CommunityService;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityListResponseDTO;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.shorts.domain.ShortsEntity;
import topetBack.topetBack.shorts.domain.ShortsResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {


	private final CommunityService communityService;
	private final SessionManager sessionManager;
	

    @PostMapping("/post")
    public String communityPost(@ModelAttribute CommunityRequestDTO communityRequestDTO, 
    		HttpServletRequest req) throws Exception  {
    	
    	
//    	Member sessionMember = sessionManager.getSessionObject(req).toMember();
//		communityRequestDTO.setAuthor(sessionMember);
    	System.out.println(communityRequestDTO.getHashtag());
    	System.out.println(communityRequestDTO);
    	CommunityResponseDTO communityResponseDTO = communityService.createCommunity(communityRequestDTO);


		//return ResponseEntity.ok(communityResponseDTO).toString();
    	return ResponseEntity.ok(communityResponseDTO).toString();
	}

	// 게시판 리스트
	 @GetMapping("/list/{animal}/{category}")
	    public ResponseEntity<List<CommunityListResponseDTO>> boardList(
																		@QuerydslPredicate(root = CommunityEntity.class) Predicate predicate,
                                                                        @PathVariable("animal") String animal,
                                                                        @PathVariable("category") String category,
                                                                        @RequestParam(name = "page") int page,
                                                                        @RequestParam(name = "size") int size,
                                                                        @RequestParam(required = false, defaultValue = "createdTime", value = "orderby") String orderby,
                                                                        HttpServletRequest req
	                                        ) throws JsonMappingException, JsonProcessingException {
		 	System.out.println("***************************************************************************************************************");
		 	System.out.println("getMappin 실행 시작 " + category);
		 	System.out.println("***************************************************************************************************************");
		 	System.out.println(predicate);
		 	System.out.println(page);
		 	System.out.println(size);
	        if ("freedomAndDaily".equals(category)) {
	            category = "자유/일상";
	        } else if ("curious".equals(category)) {
	            category = "궁금해요";
	        } else {
	            category = "정보공유";
	        }
	        Long currentUserId = sessionManager.getSessionObject(req);
	        System.out.println("orderBy : " + orderby);
	        List<CommunityListResponseDTO> communityList = communityService.getCommunityListByAnimalAndCategory(animal, category, page, size , predicate , currentUserId , orderby);

	        System.out.println 
	        (communityList);
		 	System.out.println("***************************************************************************************************************");
		 	System.out.println("getMappin 실행 종료 ");
		 	System.out.println("***************************************************************************************************************");
	        
	        
	        return new ResponseEntity<>(communityList, HttpStatus.OK);
	    }		

    //게시판 디테일
    @GetMapping("/detail/{id}")
    public ResponseEntity<CommunityResponseDTO> communityDetail(Model model, @PathVariable("id") int id) {        
        CommunityResponseDTO communityDetail = communityService.getCommunityById(id);
        
        System.out.println("communityDetail : " + communityDetail.getImages());
        
        return new ResponseEntity<>(communityDetail, HttpStatus.OK);
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
            @ModelAttribute CommunityRequestDTO communityRequestDTO
    		) throws NotFoundException, IOException {
    	System.out.println(communityRequestDTO);
        CommunityResponseDTO updatedCommunity = communityService.updateCommunity(id, communityRequestDTO);
//        return ResponseEntity.ok(updatedCommunity);
        return ResponseEntity.ok(null);
    }
    
    //사용자에 맞는 게시글 가져오기
    @GetMapping("/author/{id}")
    public ResponseEntity<List<CommunityListResponseDTO>> getCommunityByAuthorId(
    		@QuerydslPredicate(root = CommunityEntity.class) Predicate predicate,
    		@PathVariable("id") Long id,
    		@RequestParam(name = "page") int page,
    		@RequestParam(name = "size") int size){
  
    	List<CommunityListResponseDTO> communityList = communityService.findByAuthorId(id, page, size);
    	return new ResponseEntity<>(communityList, HttpStatus.OK);
    }
    
    
    //사용자기준좋아요한게시글
    @GetMapping("/likedPosts/{id}")
    public ResponseEntity<List<CommunityListResponseDTO>> getLikedCommunityByAuthorId(@QuerydslPredicate(root = CommunityEntity.class) Predicate predicate,
												@PathVariable("id") Long id,
												@RequestParam(name = "page") int page,
												@RequestParam(name = "size") int size){
											    	
    	List<CommunityListResponseDTO> communityList = communityService.getLikedCommunityByAuthorId(id, page, size);
    	return new ResponseEntity<>(communityList, HttpStatus.OK);
    }
    
  
    
    //동물통합 인기순
    @GetMapping("/{animal}/sortLike")
    public ResponseEntity<List<CommunityResponseDTO>> boardLikeAnimalList(Model model, @PathVariable("animal") String animal) {
        List<CommunityResponseDTO> communityList = communityService.getCommunityListByAnimalAndLike(animal);
        return new ResponseEntity<>(communityList, HttpStatus.OK);
    }	
    
    @GetMapping("/bestCommunity")
    public ResponseEntity<List<CommunityListResponseDTO>> bestCommunity(){
    	List<CommunityListResponseDTO> list = communityService.bestCommunity();
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    
    
    
}
