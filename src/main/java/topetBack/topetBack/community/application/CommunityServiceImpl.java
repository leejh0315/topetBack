package topetBack.topetBack.community.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import topetBack.topetBack.block.dao.BlockRepository;
import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityListResponseDTO;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.community.domain.QCommunityEntity;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.dao.FileInfoRepository;
import topetBack.topetBack.file.domain.FileCategory;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;
import topetBack.topetBack.hashTag.dao.HashTagRepository;
import topetBack.topetBack.hashTag.dao.TagMappingRepository;
import topetBack.topetBack.hashTag.domain.HashTagEntity;
import topetBack.topetBack.hashTag.domain.TagMapping;
import static topetBack.topetBack.file.domain.QFileInfoEntity.fileInfoEntity;
@Service
public class CommunityServiceImpl implements CommunityService {

	private final CommunityRepository communityRepository;
	private final HashTagRepository hashTagRepository;
	private final FileService fileService;
	private final BlockRepository blockRepository;
	private final CommentRepository commentRepository;
	private final TagMappingRepository tagMappingRepository;
	private final FileInfoRepository fileInfoRepository;
	
	 private final JPAQueryFactory queryFactory;

	public CommunityServiceImpl(CommunityRepository communityRepository, FileService fileService,
			CommentRepository commentRepository, FileInfoRepository fileInfoRepository, BlockRepository blockRepository, JPAQueryFactory queryFactory,
			HashTagRepository hashTagRepository, TagMappingRepository tagMappingRepository) {
		this.communityRepository = communityRepository;
		this.fileService = fileService;
		this.commentRepository = commentRepository;
		this.blockRepository = blockRepository;
		this.hashTagRepository = hashTagRepository;
		this.tagMappingRepository = tagMappingRepository;
		this.fileInfoRepository = fileInfoRepository;
		this.queryFactory = queryFactory;
	}

	@Override
	@Transactional
	public CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO) throws IOException {
		CommunityEntity communityEntity = communityRequestDTO.toCommunityEntity();

		if (communityRequestDTO.getImages() != null && !communityRequestDTO.getImages().isEmpty()) {
			FileGroupEntity fileGroupEntity = communityEntity.getFileGroupEntity();

			List<FileInfoEntity> fileInfoList = new ArrayList<>();
			for (MultipartFile file : communityRequestDTO.getImages()) {
				FileInfoEntity fileInfo = fileService.storeFile(file, FileCategory.COMMUNITY);
				fileInfo.setFileGroupEntity(fileGroupEntity);
				fileInfoList.add(fileInfo);
			}
			fileGroupEntity.setFileList(fileInfoList);
		}

		if (communityRequestDTO.getHashtag() != null && !communityRequestDTO.getHashtag().isEmpty()) {

			TagMapping tagMapping = communityEntity.getTagMappings();
			List<HashTagEntity> hashTagList = new ArrayList<>();
			for (String tag : communityRequestDTO.getHashtag()) {

				HashTagEntity hashTagEntity = HashTagEntity.builder().tag(tag).build();
				hashTagEntity.setTagMapping(tagMapping);
				hashTagRepository.save(hashTagEntity);
				hashTagList.add(hashTagEntity);
			}
			tagMappingRepository.save(tagMapping);
		}
		System.out.println("b");

		CommunityEntity result = communityRepository.save(communityEntity);

		return result.toResponseDTO();
	}

	@Override
	public CommunityResponseDTO getCommunityById(long communityId) {

		return communityRepository.findById(communityId).get().toResponseDTO();
	}

	// 게시판 리스트
	@Override
	public List<CommunityListResponseDTO> getCommunityListByAnimalAndCategory(String animal, String category, int page,
			int size, Predicate predicate, Long currentUserId, String orderby) {
		
		PageRequest pageable = PageRequest.of(page, size);
		// 차단된 유저의 게시물을 제외하는 필터
		List<Long> blockedUserIds = blockRepository.findBlockedUserIdsByBlocker(currentUserId);

		// 기본 필터링 추가
		BooleanExpression baseFilter = QCommunityEntity.communityEntity.animal.eq(animal)
				.and(QCommunityEntity.communityEntity.category.eq(category))
				.and(QCommunityEntity.communityEntity.author.id.notIn(blockedUserIds));

		// baseFilter와 전달된 predicate를 결합
		Predicate combinedPredicate = baseFilter.and(predicate);

		System.out.println("검색어 확인: " + combinedPredicate);

		// 동적 쿼리와 페이징을 함께 사용하여 정렬 기준(orderby)에 따라 결과를 가져옴
		Slice<CommunityEntity> communityEntitySlice = communityRepository.findAllWithPredicate(predicate, pageable,
				orderby, animal, category);

		System.out.println(communityEntitySlice);

		return communityEntitySlice.stream().map(CommunityEntity::toListResponseDTO).collect(Collectors.toList());
	}

	// 삭제
	@Override
	public void deleteCommunity(Long post_id) throws NotFoundException {
		CommunityEntity community = communityRepository.findById(post_id)
				.orElseThrow(() -> new NotFoundException("해당 커뮤니티를 찾을 수 없습니다: " + post_id));
		commentRepository.deleteByCommunityId(post_id);
		communityRepository.deleteById(post_id);
	}

	// 수정
	@Transactional
	public CommunityResponseDTO updateCommunity(Long id, CommunityRequestDTO communityRequestDTO

	) throws NotFoundException, IOException {
		CommunityEntity communityEntity = communityRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("해당 커뮤니티를 찾을 수 없습니다: " + id));

		String title = Optional.ofNullable(communityRequestDTO.getTitle()).orElse(communityEntity.getTitle());
		String content = Optional.ofNullable(communityRequestDTO.getContent()).orElse(communityEntity.getContent());

		TagMapping tagMapping = communityEntity.getTagMappings(); // 5번
		FileGroupEntity fileGroupEntity = communityEntity.getFileGroupEntity();

		if (communityRequestDTO.getHashtag() != null && !communityRequestDTO.getHashtag().isEmpty()) {
			hashTagRepository.deleteByTagMappingId(tagMapping);
			List<HashTagEntity> hashTagList = new ArrayList<>();
			for (String tag : communityRequestDTO.getHashtag()) {

				HashTagEntity hashTagEntity = HashTagEntity.builder().tag(tag).build();
				hashTagEntity.setTagMapping(tagMapping);
				hashTagRepository.save(hashTagEntity);
				hashTagList.add(hashTagEntity);
			}
			tagMappingRepository.save(tagMapping);
		} else {
			hashTagRepository.deleteByTagMappingId(tagMapping);

		}
		System.out.println("*********************************************************");
		if (communityRequestDTO.getDeletedPhoto() != null && communityRequestDTO.getDeletedPhoto().size() > 0) {
			for (Long fileId: communityRequestDTO.getDeletedPhoto()) {
				System.out.println("deldeldeldeldeldeldeldeldeldeldeldeldel ");
				System.out.println(fileId);
				
				Long query = queryFactory.delete(fileInfoEntity).where(fileInfoEntity.id.eq(fileId)).execute();
//				fileInfoRepository.deleteById(fileId);
			}
		}
		System.out.println("*********************************************************");
		
		if (communityRequestDTO.getEditPhoto() != null && communityRequestDTO.getEditPhoto().size() > 0) {
			List<FileInfoEntity> fileInfoList = new ArrayList<>();
			for (MultipartFile file : communityRequestDTO.getEditPhoto()) {
				FileInfoEntity fileInfo = fileService.storeFile(file, FileCategory.COMMUNITY);
				fileInfo.setFileGroupEntity(fileGroupEntity);
				fileInfoList.add(fileInfo);
			}
		}
		communityEntity.updateCommunity(title, content);
		CommunityEntity updatedCommunity = communityRepository.save(communityEntity);

		return updatedCommunity.toResponseDTO();
	}

	// 내 게시글 보기
	@Override
	public List<CommunityListResponseDTO> findByAuthorId(Long id, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Slice<CommunityEntity> myCommunityList = communityRepository.findByAuthorId(pageable, id);
		return myCommunityList.stream().map(CommunityEntity::toListResponseDTO).collect(Collectors.toList());
	}

	@Transactional
	public List<CommunityResponseDTO> getCommunityListByAnimalAndLike(String animal) {
		List<CommunityEntity> communityEntityList = communityRepository.findAnimalOrderByLikesCountDesc(animal);
		return communityEntityList.stream().map(CommunityEntity::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<CommunityListResponseDTO> getLikedCommunityByAuthorId(Long authorId, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Slice<CommunityEntity> likedList = communityRepository.getLikedCommunityByAuthorId(pageable, authorId);
		return likedList.stream().map(CommunityEntity::toListResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<CommunityListResponseDTO> bestCommunity() {
		List<CommunityEntity> list = communityRepository.bestCommunity();
		return list.stream().map(CommunityEntity::toListResponseDTO).collect(Collectors.toList());
	}

}