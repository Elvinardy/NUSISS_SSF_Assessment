package iss.ssf_assessment_book_search.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import iss.ssf_assessment_book_search.Constants;

@Configuration
public class RedisConfig {
    
    public static final Logger logger = LoggerFactory.getLogger(RedisConfig.class.getName());
    
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    private final String redisPassword;

    public RedisConfig() {
        redisPassword = System.getenv(Constants.ENV_REDIS_PASSWORD);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateFactory() {

        final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort.get());
        if (redisPassword != null) {
            redisConfig.setPassword(redisPassword);
            logger.info("Set Redis password");
        }
        redisConfig.setDatabase(redisDatabase);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(redisConfig, jedisClient);
        jedisFac.afterPropertiesSet();

        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        // Java string is in 2bytes
        return template;
    }
}
