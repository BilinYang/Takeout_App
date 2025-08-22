package com.bilin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// Don't necessarily have to write a custom Redis Configuration class
@Slf4j
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        log.info("Initialize RedisTemplate");
        // Set serializer for key (default is Jackson2JsonRedisSerializer)
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // this line (redisTemplate.setValueSerializer(new StringRedisSerializer())) is NOT recommended because if we use it,
        // then it will not automatically convert types (like converting list to string) for us anymore and can only process strings

        // Create object with Redis Connection Factory
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
