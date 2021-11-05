package com.playssafy.playssafy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// WebMvcConfigurer를 인터페이스를 implements한다.
public class WebConfig implements WebMvcConfigurer {
    // CORS 관련 설정을 오버라이딩한다.
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping(("/**")) // CORS를 적용할 URL 패턴을 정의한다. 여기서는 와일드카드(/**) 사용.
                .allowedOrigins("*") // 자원 공유를 허락할 Origins 설정.
                .allowedMethods("*"); // 자원 공유를 허락할 HTTP Method 요청 정의.
    }
}
