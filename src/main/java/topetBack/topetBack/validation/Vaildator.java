package topetBack.topetBack.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import topetBack.topetBack.form.CommunityForm;
import topetBack.topetBack.form.CommunityPostForm;

@Component
public class Vaildator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
	      return CommunityPostForm.class.isAssignableFrom(clazz);
	   }

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CommunityPostForm communityPostForm = (CommunityPostForm)target;
	      
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
