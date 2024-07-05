package topetBack.topetBack.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_test")
@ToString
@Entity
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용    
	private Long id;
	
	@Column(length = 200, nullable = false)
	private String testText;
}
