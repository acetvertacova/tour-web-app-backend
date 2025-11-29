package com.tour_web_app.config;

import com.tour_web_app.entity.Tour;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisConfig {

    private Duration timeToLive = Duration.ofMinutes(15);

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(timeToLive)
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

    @Bean
    public RedisTemplate<String, Tour> redisTemplate(RedisConnectionFactory connectionFactory) {
        //class for operations with Redis(save, create, delete data)
        RedisTemplate<String, Tour> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use Jackson2JsonRedisSerializer for serialization
        Jackson2JsonRedisSerializer<Tour> serializer = new Jackson2JsonRedisSerializer<>(Tour.class);
        template.setValueSerializer(serializer);

        return template;
    }
}
