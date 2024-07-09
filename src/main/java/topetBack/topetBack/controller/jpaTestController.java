package topetBack.topetBack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import topetBack.topetBack.domain.Test;
import topetBack.topetBack.repository.TestRepository;

@RestController
@RequiredArgsConstructor
public class jpaTestController {
    private final TestRepository testRepository;
    @PostMapping("/jpa/test")
    public void JpaTest(@RequestBody Test test) {
    	testRepository.save(test);
    }
}

