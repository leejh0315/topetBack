package topetBack.topetBack.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import topetBack.topetBack.domain.Schedule;
import topetBack.topetBack.form.CommunityPostForm;

@Component
public class ScheduleVaildator implements Validator{
	SimpleDateFormat  dateFormat = new  SimpleDateFormat("yyyy-MM-dd");

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
	      return Schedule.class.isAssignableFrom(clazz);
	}
	
	
	@Override
	public void validate(Object target, Errors errors) {
		 
		// TODO Auto-generated method stub
		Schedule schedule = (Schedule)target;
		
		
        
	      if(!StringUtils.hasText(schedule.getScheduleContent())) {
	         errors.rejectValue("scheduleContent", null, "내용을 작성해주세요.");         
	      }
	      if(!StringUtils.hasText(schedule.getScheduleTitle())) {
		         errors.rejectValue("scheduleTitle", null, "제목을 작성해주세요.");         
		  }
	      if(!StringUtils.hasText(schedule.getColor())) {
		         errors.rejectValue("scheduleTitle", null, "컬러를 작성해주세요.");         
		  }
	      if(!schedule.getEndDate().isAfter(schedule.getStartDate())) {
		         errors.rejectValue("scheduleTitle", null, "일정을 맞춰주세요.");         
		  }
	      
	}

}
