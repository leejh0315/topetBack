package topetBack.topetBack.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.global.session.SessionVar;
import topetBack.topetBack.user.domain.Member;

@RestController
@Slf4j
public class MainController {

    @GetMapping("/api/temp")
    @ResponseBody
    public String getHome( HttpServletRequest req) {
        log.info("get Home");

        HttpSession session = req.getSession(false);
		Member member = (Member) session.getAttribute(SessionVar.LOGIN_MEMBER);
		
		log.info(member.toString());
		
        return "Back Front Connected!";
    }

    
}
