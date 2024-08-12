package topetBack.topetBack.block.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import topetBack.topetBack.block.dao.BlockRepository;
import topetBack.topetBack.block.domain.BlockEntity;
import topetBack.topetBack.block.domain.BlockRequestDTO;
import topetBack.topetBack.block.domain.BlockResponseDTO;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;

@Service
public class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    public BlockServiceImpl(BlockRepository blockRepository, MemberRepository memberRepository) {
        this.blockRepository = blockRepository;
        this.memberRepository = memberRepository;
    }

    private Member findMemberById(Long memberId, String errorMessage) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(errorMessage));
    }

    // 차단
    @Transactional
    public void blockUser(BlockRequestDTO blockRequestDTO) {
        Member blocker = findMemberById(blockRequestDTO.getBlocker().getId(), "차단 사용자 못찾음");
        Member blocked = findMemberById(blockRequestDTO.getBlocked().getId(), "차단자 못찾음");

        if (!blockRepository.existsByBlockerAndBlocked(blocker, blocked)) {
            BlockEntity blockEntity = blockRequestDTO.toBlockEntity();
            blockRepository.save(blockEntity);
        }
     
    }

    // 차단 해제
    @Transactional
    public void unblockUser(BlockRequestDTO blockRequestDTO) {
        Member blocker = findMemberById(blockRequestDTO.getBlocker().getId(), "차단 사용자 못찾음");
        Member blocked = findMemberById(blockRequestDTO.getBlocked().getId(), "차단자 못찾음");

        blockRepository.deleteByBlockerAndBlocked(blocker, blocked);
    }

    // 차단 여부 확인
    public boolean isBlocked(Long blockerId, Long blockedId) {
        Member blocker = findMemberById(blockerId, "차단 사용자 못찾음");
        Member blocked = findMemberById(blockedId, "차단자 못찾음");

        return blockRepository.existsByBlockerAndBlocked(blocker, blocked);
    }
}
