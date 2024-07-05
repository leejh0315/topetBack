package topetBack.topetBack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.form.PostForm;

@RestController
@Slf4j
public class ScheduleController {

	@GetMapping("/api/schedule")
    public String getCalendar() {
        log.info("get Calendar");
        return "get Calendar!";
    }
    //get post 
    @PostMapping("/api/postTemp")
    public String postHome(@RequestBody PostForm postForm) {
        log.info("postName : " + postForm);
        System.out.println(postForm.getPostTitle());
        System.out.println(postForm.getContent());
        
        return "입력한 내용은 " + postForm;
    }
}

