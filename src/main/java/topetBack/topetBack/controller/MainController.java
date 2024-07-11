package topetBack.topetBack.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MainController {

    @GetMapping("/api/temp")
    @ResponseBody
    public String getHome() {
        log.info("get Home");
        return "Back Front Connected!";
    }
    //get post    
    
}
