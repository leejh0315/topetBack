package topetBack.topetBack.community.application;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import topetBack.topetBack.comment.dao.CommentRepository;
import topetBack.topetBack.comment.domain.CommentRequestDTO;
import topetBack.topetBack.community.dao.CommunityRepository;
import topetBack.topetBack.community.domain.CommunityEntity;
import topetBack.topetBack.community.domain.CommunityRequestDTO;
import topetBack.topetBack.community.domain.CommunityResponseDTO;
import topetBack.topetBack.file.application.FileService;
import topetBack.topetBack.file.domain.FileCategory;

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

        fileService.uploadPhoto(communityRequestDTO.getImages(), communityEntity.getFileGroupEntity(), FileCategory.COMMUNITY.getPath());

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

    @Override
    public List<CommunityResponseDTO> getCommunityListByAnimalAndCategory(String animal , String category) {
        List<CommunityEntity> communityEntityList = communityRepository.findByAnimalAndCategory(animal , category);
        return communityEntityList.stream()
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
        System.out.println("테스트: " + title + content);

        CommunityEntity updatedCommunity = communityRepository.save(communityEntity);

        return updatedCommunity.toResponseDTO();
    }


}