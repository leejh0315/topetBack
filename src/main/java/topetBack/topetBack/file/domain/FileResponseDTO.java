package topetBack.topetBack.file.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponseDTO {

    private String origFileName;  // 파일 원본명

    private String filePath;  // 파일 저장 경로
    
    private String newFileName;	//파일 새로운 이름

}
