package topetBack.topetBack.block.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topetBack.topetBack.comment.domain.CommentEntity;

import topetBack.topetBack.member.domain.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockRequestDTO {
	private Long id;
	private Member blocker;
	private Member blocked;
	
	public BlockEntity toBlockEntity() {
		BlockEntity.BlockEntityBuilder builder = BlockEntity.builder()
				.id(this.id)
				.blocker(this.blocker)
				.blocked(this.blocked);
		
        return builder.build();
		 
	}
}
