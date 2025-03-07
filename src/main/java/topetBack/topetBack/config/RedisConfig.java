package topetBack.topetBack.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@Configuration
@EnableRedisHttpSession
public class RedisConfig {
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
 // 
//	 @Bean
//	    public RedisConnectionFactory redisConnectionFactory() {
//	        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("175.45.202.131", port);
//
//	        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
//	                .commandTimeout(Duration.ofMillis(5000)) // 예: 연결 타임아웃 5초로 설정
//	                .build();
//
//	        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
//	    }
    
    
    @Bean
    public RedisTemplate<String, Long> redisTemplate() {
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
