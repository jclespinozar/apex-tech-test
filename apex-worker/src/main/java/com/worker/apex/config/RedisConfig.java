package com.worker.apex.config;

import com.worker.apex.redis.FailedOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, FailedOrder> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, FailedOrder> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(RedisSerializer.json());
        return template;
    }
}
