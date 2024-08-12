package topetBack.topetBack.block;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import topetBack.topetBack.block.application.BlockService;
import topetBack.topetBack.block.domain.BlockRequestDTO;
import topetBack.topetBack.block.domain.BlockResponseDTO;
import topetBack.topetBack.member.dao.MemberRepository;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.report.domain.ReportEntitiy;

@RestController
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private BlockService blockService;
    
    @Autowired
    private MemberRepository memberRepository;
    

    // 차단
    @PostMapping("/")
    public ResponseEntity<String> blockUser(@RequestParam(name = "blockerId") Long blockerId, @RequestParam(name = "blockedId") Long blockedId) {
    	//memberservice findid로 바꿀예정
    	Member blocker = memberRepository.findById(blockerId)
                .orElseThrow(() -> new RuntimeException("차단 사용자 못찾음"));
        Member blocked = memberRepository.findById(blockedId)
                .orElseThrow(() -> new RuntimeException("차단자 못찾음"));

        BlockRequestDTO blockRequestDTO = BlockRequestDTO.builder()
                .blocker(blocker)
                .blocked(blocked)
                .build();

        blockService.blockUser(blockRequestDTO);
        return ResponseEntity.ok("차단 완료");
    }

    // 차단 해제
    @PostMapping("/unblock")
    public ResponseEntity<String> unblockUser(@RequestParam(name = "blockerId") Long blockerId, @RequestParam(name = "blockedId") Long blockedId) {
    	//memberservice findid로 바꿀예정
    	Member blocker = memberRepository.findById(blockerId)
                .orElseThrow(() -> new RuntimeException("차단 사용자 못찾음"));
        Member blocked = memberRepository.findById(blockedId)
                .orElseThrow(() -> new RuntimeException("차단자 못찾음"));

        BlockRequestDTO blockRequestDTO = BlockRequestDTO.builder()
                .blocker(blocker)
                .blocked(blocked)
                .build();

        blockService.unblockUser(blockRequestDTO);
        return ResponseEntity.ok("차단 해제");
    }

    // 차단 여부 확인
    @GetMapping("/is-blocked")
    public ResponseEntity<Boolean> isUserBlocked(@RequestParam(name = "blockerId") Long blockerId, @RequestParam(name = "blockedId") Long blockedId) {
        boolean isBlocked = blockService.isBlocked(blockerId, blockedId);
        return ResponseEntity.ok(isBlocked);
    }
    
    //내 차단 리스트
    @GetMapping("myBlockList/{id}")
    public ResponseEntity<List<BlockResponseDTO>> findByMyBlockList(@PathVariable("id") Long id){
    	List<BlockResponseDTO> myBlockList = blockService.findByAuthorId(id);
    	return new ResponseEntity<>(myBlockList, HttpStatus.OK);
    	
    	
    }
}
