package topetBack.topetBack.community.application;

import java.io.IOException;
import java.util.ArrayList;
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

import org.springframework.web.multipart.MultipartFile;

import topetBack.topetBack.block.dao.BlockRepository;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.*;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
	private final FileService fileService;
    private final BlockRepository blockRepository;
	private final CommentRepository commentRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository, FileService fileService , CommentRepository commentRepository , BlockRepository blockRepository) {
        this.communityRepository = communityRepository;
        this.fileService = fileService;
        this.commentRepository = commentRepository;
        this.blockRepository = blockRepository;
    }

    @Override
    @Transactional
    public CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO) throws IOException {
        CommunityEntity communityEntity = communityRequestDTO.toCommunityEntity();

        if (communityRequestDTO.getImages() != null && !communityRequestDTO.getImages().isEmpty()) {
        	System.out.println("이미지가있다");
            FileGroupEntity fileGroupEntity = communityEntity.getFileGroupEntity();
            List<FileInfoEntity> fileInfoList = new ArrayList<>();

            for (MultipartFile file : communityRequestDTO.getImages()) {
                FileInfoEntity fileInfo = fileService.storeFile(file, FileCategory.COMMUNITY);
                fileInfo.setFileGroupEntity(fileGroupEntity);
                fileInfoList.add(fileInfo);
            }

            fileGroupEntity.setFileList(fileInfoList);
        }

        CommunityEntity result = communityRepository.save(communityEntity);

        return result.toResponseDTO();
    }

    @Override
    public CommunityResponseDTO getCommunityById(long communityId){	
    	
        return communityRepository.findById(communityId).get().toResponseDTO();
    }
    
    //게시판 리스트
    @Override
    public List<CommunityListResponseDTO> getCommunityListByAnimalAndCategory(String animal, String category, int page, int size, Predicate predicate, Long currentUserId, String orderby) {
        PageRequest pageable = PageRequest.of(page, size);

        // 차단된 유저의 게시물을 제외하는 필터
//        List<Long> blockedUserIds = blockRepository.findBlockedUserIdsByBlocker(currentUserId);
//
//        // 기본 필터링 추가
//        BooleanExpression baseFilter = QCommunityEntity.communityEntity.animal.eq(animal)
//                .and(QCommunityEntity.communityEntity.category.eq(category))
//                .and(QCommunityEntity.communityEntity.author.id.notIn(blockedUserIds));
//
//        // baseFilter와 전달된 predicate를 결합
//        Predicate combinedPredicate = baseFilter.and(predicate);
//
//        System.out.println("검색어 확인: " + combinedPredicate);

        // 동적 쿼리와 페이징을 함께 사용하여 정렬 기준(orderby)에 따라 결과를 가져옴
        Slice<CommunityEntity> communityEntitySlice = communityRepository.findAllWithPredicate(predicate, pageable, orderby, animal, category);

        return communityEntitySlice.stream()
                .map(CommunityEntity::toListResponseDTO)
                .collect(Collectors.toList());
    }

    //삭제
    @Override
    public void deleteCommunity(Long post_id) throws NotFoundException {
    	   CommunityEntity community = communityRepository.findById(post_id)
                   .orElseThrow(() -> new NotFoundException("해당 커뮤니티를 찾을 수 없습니다: " + post_id));
    	commentRepository.deleteByCommunityId(post_id);
        communityRepository.deleteById(post_id);
    }
    
    //수정
    @Transactional
    public CommunityResponseDTO updateCommunity(Long id, CommunityRequestDTO communityRequestDTO) throws NotFoundException {
        CommunityEntity communityEntity = communityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 커뮤니티를 찾을 수 없습니다: " + id));

        String title = Optional.ofNullable(communityRequestDTO.getTitle()).orElse(communityEntity.getTitle());
        String content = Optional.ofNullable(communityRequestDTO.getContent()).orElse(communityEntity.getContent());
        String hashtag = Optional.ofNullable(communityRequestDTO.getHashtag()).orElse(communityEntity.getHashtag());

        communityEntity.updateCommunity(title, content , hashtag);

        CommunityEntity updatedCommunity = communityRepository.save(communityEntity);

        return updatedCommunity.toResponseDTO();
    }

    //내 게시글 보기
	@Override
	public List<CommunityListResponseDTO> findByAuthorId(Long id, int page, int size) {		
		PageRequest pageable = PageRequest.of(page, size);
		Slice<CommunityEntity> myCommunityList = communityRepository.findByAuthorId(pageable, id);
		return myCommunityList.stream()
                .map(CommunityEntity::toListResponseDTO)
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