package com.convertools.config;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * httpclient 配置
 */
@Configuration
public class HttpClientConfig {

    @Bean
    public Builder httpBuilder() {
        return new Builder();
    }

    @Bean
    public OkHttpClient httpClient(Builder builder) {
        return  builder.build();
    }
}
