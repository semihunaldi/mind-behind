package com.mindbehind.demo.config;

import com.mindbehind.demo.mapper.CommentMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by semihunaldi on 28.06.2021
 */

@Configuration
@EnableConfigurationProperties({MindBehindProperties.class})
public class MindBehindConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommentMapper commentMapper() {
        return Mappers.getMapper(CommentMapper.class);
    }
}
