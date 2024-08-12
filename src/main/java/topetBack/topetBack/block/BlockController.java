package topetBack.topetBack.block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import topetBack.topetBack.block.application.BlockService;
import topetBack.topetBack.block.domain.BlockRequestDTO;

@RestController
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    // 차단
    @PostMapping("/block")
    public ResponseEntity<String> blockUser(@RequestParam Long blockerId, @RequestParam Long blockedId) {
        BlockRequestDTO blockRequestDTO = BlockRequestDTO.builder()
                .blocker(BlockRequestDTO.builder().id(blockerId).build().getBlocker())
                .blocked(BlockRequestDTO.builder().id(blockedId).build().getBlocked())
                .build();

        blockService.blockUser(blockRequestDTO);
        return ResponseEntity.ok("차단 완료");
    }

    // 차단 해제
    @PostMapping("/unblock")
    public ResponseEntity<String> unblockUser(@RequestParam Long blockerId, @RequestParam Long blockedId) {
        BlockRequestDTO blockRequestDTO = BlockRequestDTO.builder()
                .blocker(BlockRequestDTO.builder().id(blockerId).build().getBlocker())
                .blocked(BlockRequestDTO.builder().id(blockedId).build().getBlocked())
                .build();

        blockService.unblockUser(blockRequestDTO);
        return ResponseEntity.ok("차단 해제");
    }

    // 차단 여부 확인
    @GetMapping("/is-blocked")
    public ResponseEntity<Boolean> isUserBlocked(@RequestParam Long blockerId, @RequestParam Long blockedId) {
        boolean isBlocked = blockService.isBlocked(blockerId, blockedId);
        return ResponseEntity.ok(isBlocked);
    }
}
