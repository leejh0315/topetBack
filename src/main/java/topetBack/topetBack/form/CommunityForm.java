package topetBack.topetBack.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor // 기본 생성자 추가
public class CommunityForm {
   private String title;
   private String content;
   private String hashtag;
   
   @Builder
   CommunityForm(String title , String content, String hashtag){
      this.title = title;
      this.content = content;
      this.hashtag = hashtag;
   }
}
