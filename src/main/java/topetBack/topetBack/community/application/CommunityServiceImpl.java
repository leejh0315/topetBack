package topetBack.topetBack.community.application;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.community.domain.QCommunityEntity;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
	private final FileService fileService;
	private final CommentRepository commentRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository, FileService fileService , CommentRepository commentRepository) {
        this.communityRepository = communityRepository;
        this.fileService = fileService;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO) throws IOException {
        CommunityEntity communityEntity = communityRequestDTO.toCommunityEntity();
        
        if (communityRequestDTO.getImages() != null && !communityRequestDTO.getImages().isEmpty()) {
            FileGroupEntity fileGroupEntity = fileService.uploadPhoto(
                communityRequestDTO.getImages(),
                communityEntity.getFileGroupEntity(),
                FileCategory.COMMUNITY.getPath()
            );

        }
        

        CommunityEntity result = communityRepository.save(communityEntity);

        return result.toResponseDTO();
    }

    @Override
    public CommunityResponseDTO getCommunityById(long communityId){
    	
        return communityRepository.findById(communityId).get().toResponseDTO();
    }

    @Override
    public List<CommunityResponseDTO> getCommunityListByCategory(String category) {
        List<CommunityEntity> communityEntityList = communityRepository.findByCategory(category);
        return communityEntityList.stream()
                .map(CommunityEntity::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CommunityResponseDTO> getCommunityListByAnimalAndCategory(String animal, String category, int page, int size , Predicate predicate) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));
        // 기본 필터링 추가
        BooleanExpression baseFilter = QCommunityEntity.communityEntity.animal.eq(animal)
                .and(QCommunityEntity.communityEntity.category.eq(category));

        // baseFilter와 전달된 predicate를 결합
        Predicate combinedPredicate = baseFilter.and(predicate);
        
        System.out.println("검색어 확인" + combinedPredicate);
        // 동적 쿼리와 페이징을 함께 사용
        Slice<CommunityEntity> communityEntitySlice = communityRepository.findAll(combinedPredicate, pageable);
        return communityEntitySlice.stream()
                .map(CommunityEntity::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommunity(Long post_id) throws NotFoundException {
    	   CommunityEntity community = communityRepository.findById(post_id)
                   .orElseThrow(() -> new NotFoundException("해당 커뮤니티를 찾을 수 없습니다: " + post_id));
    	commentRepository.deleteByCommunityId(post_id);
        communityRepository.deleteById(post_id);
    }
    
    @Transactional
    public CommunityResponseDTO updateCommunity(Long id, CommunityRequestDTO communityRequestDTO) throws NotFoundException {
        CommunityEntity communityEntity = communityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 커뮤니티를 찾을 수 없습니다: " + id));

        String title = Optional.ofNullable(communityRequestDTO.getTitle()).orElse(communityEntity.getTitle());
        String content = Optional.ofNullable(communityRequestDTO.getContent()).orElse(communityEntity.getContent());
        communityEntity.updateCommunity(title, content);

        CommunityEntity updatedCommunity = communityRepository.save(communityEntity);

        return updatedCommunity.toResponseDTO();
    }

	@Override
	public List<CommunityResponseDTO> findByAuthorId(Long id) {		//내 게시글 보기
		List<CommunityEntity> myCommunityList = communityRepository.findByAuthorId(id);
		return myCommunityList.stream()
                .map(CommunityEntity::toResponseDTO)
                .collect(Collectors.toList());
	}
	
	@Transactional
	public List<CommunityResponseDTO> getCommunityListByAnimalAndCategoryAndLike(String animal , String category , int page , int size){
        PageRequest pageable = PageRequest.of(page, size);
        Slice<CommunityEntity> communityEntityList = communityRepository.findAllOrderByLikesCountDesc(animal , category , pageable);
        return communityEntityList.stream()
                .map(CommunityEntity::toResponseDTO)
                .collect(Collectors.toList());
	}
	
	@Transactional
	public List<CommunityResponseDTO> getCommunityListByAnimalAndLike(String animal){
		List<CommunityEntity> communityEntityList = communityRepository.findAnimalOrderByLikesCountDesc(animal);
        return communityEntityList.stream()
                .map(CommunityEntity::toResponseDTO)
                .collect(Collectors.toList());
	}
	
	
	
	


}