package com.humuson.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "humuson")
public class AppProperties {

    @Value("${humuson.external.type}")
    public String externalType;

    @Value("${humuson.external.http.url}")
    public String externalHttpUrl;

    @Value("${humuson.external.http.token.key}")
    public String externalHttpTokenKey;

    @Value("${humuson.external.http.token.value}")
    public String externalHttpTokenValue;

    @Value("${humuson.external.file.path}")
    public String externalFilePath;
}
