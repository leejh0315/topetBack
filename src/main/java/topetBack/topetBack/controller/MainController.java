package topetBack.topetBack.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.form.PostForm;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {
    //temp
    @GetMapping("/api/temp")
    @ResponseBody
    public String getHome() {
        log.info("get Home");
        return "Back Front Connected!";
    }
    
    @PostMapping("/api/postTemp")
    public String postHome(@RequestBody PostForm postForm) {
        log.info("postName : " + postForm);
        System.out.println(postForm.getPostTitle());
        System.out.println(postForm.getContent());
        
        return "입력한 내용은 " + postForm;
    }
}
