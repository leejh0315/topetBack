package topetBack.topetBack.community.application;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
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

    public CommunityServiceImpl(CommunityRepository communityRepository, FileService fileService) {
        this.communityRepository = communityRepository;
        this.fileService = fileService;
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
    };

    @Override
    public List<CommunityResponseDTO> getCommunityListByCategory(String category) {
        List<CommunityEntity> communityEntityList = communityRepository.findByCategory(category);
        return communityEntityList.stream()
                .map(CommunityEntity::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommunity(Long post_id) {
        communityRepository.deleteById(post_id);
    }


}