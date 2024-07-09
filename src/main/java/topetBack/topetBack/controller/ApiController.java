package topetBack.topetBack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
public class ApiController {

    @GetMapping("/api/getMapInfo")
    public String getMapInfo() {
        String jsonString = null;
        
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString("https://openapi.naver.com/v1/search/local.json")
                    .queryParam("query", "동물병원")
                    .encode(StandardCharsets.UTF_8)
                    .build()
                    .toUri();
        
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> req = RequestEntity
                  .get(uri)
                  .header("Content-Type", "application/json")
                  .header("X-Naver-Client-Id", "Dyc4blhR0Ddpb_DHWOXX")
                  .header("X-Naver-Client-Secret", "PewNhgrwjg")
                  .build();
        
             ResponseEntity<String> result = restTemplate.exchange(req, String.class);
             jsonString = result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return jsonString;
    }
}
