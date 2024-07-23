package topetBack.topetBack.schedule.validation;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import topetBack.topetBack.schedule.domain.ScheduleEntity;
import topetBack.topetBack.schedule.domain.ScheduleRequestDTO;

@Component
public class ScheduleValidator implements Validator{
	SimpleDateFormat  dateFormat = new  SimpleDateFormat("yyyy-MM-dd");

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
	      return ScheduleEntity.class.isAssignableFrom(clazz);
	}
	
	
	@Override
	public void validate(Object target, Errors errors) {
		 
		// TODO Auto-generated method stub
		ScheduleRequestDTO scheduleReqDTO = (ScheduleRequestDTO)target;
		ScheduleEntity scheduleEntity = scheduleReqDTO.toScheduleEntity();
		
        
//	      if(!StringUtils.hasText(scheduleEntity.getScheduleContent())) {
//	         errors.rejectValue("scheduleContent", null, "내용을 작성해주세요.");         
//	      }
	      if(!StringUtils.hasText(scheduleEntity.getTitle())) {
		         errors.rejectValue("scheduleTitle", null, "제목을 작성해주세요.");         
		  }
	      if(!StringUtils.hasText(scheduleEntity.getColor())) {
		         errors.rejectValue("scheduleTitle", null, "컬러를 작성해주세요.");         
		  }
	      if(!scheduleEntity.getEndDate().isAfter(scheduleEntity.getStartDate())) {
		         errors.rejectValue("scheduleTitle", null, "일정을 맞춰주세요.");         
		  }
	      
	}

}
