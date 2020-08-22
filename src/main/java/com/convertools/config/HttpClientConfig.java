package com.convertools.config;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Bean
    public Builder httpBuilder() {
        return new Builder();
    }
}
