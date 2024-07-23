package topetBack.topetBack.community.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import topetBack.topetBack.community.domain.CommunityEntity;

@Component
public class CommunityVaildator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
	      return CommunityEntity.class.isAssignableFrom(clazz);
	   }

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CommunityEntity communityPostForm = (CommunityEntity)target;

	      if(!StringUtils.hasText(communityPostForm.getContent())) {
	         errors.rejectValue("content", null, "내용 작성해주세요.");
	      }
	      if(!StringUtils.hasText(communityPostForm.getTitle())) {
	         errors.rejectValue("title", null, "제목을 작성해주세요.");
	      }
	      if(!StringUtils.hasText(communityPostForm.getCategory())) {
		         errors.rejectValue("category", null, "카테고리를 작성해주세요.");
		  }


	}

}
