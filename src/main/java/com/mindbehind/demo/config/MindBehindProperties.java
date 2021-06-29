package com.mindbehind.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by semihunaldi on 28.06.2021
 */

@ConfigurationProperties("mind-behind")
@Data
public class MindBehindProperties {

    private String commentsServiceURL;
}
