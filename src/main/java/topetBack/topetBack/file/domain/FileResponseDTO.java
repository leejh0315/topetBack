package topetBack.topetBack.file.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponseDTO {

    private String path;  // 파일 저장 경로

}
