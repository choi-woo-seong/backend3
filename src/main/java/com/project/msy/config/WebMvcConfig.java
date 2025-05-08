// src/main/java/com/project/msy/config/WebMvcConfig.java
package com.project.msy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 1) root (“/”) 요청은 무조건 index.html
        registry.addViewController("/").setViewName("forward:/index.html");

        // 2) 첫 세그먼트가 api 가 아닌 단일 세그먼트
        registry.addViewController("/{_:^(?!api$).+}")
                .setViewName("forward:/index.html");

        // 3) 첫 세그먼트가 api 가 아닌 다중 세그먼트
        registry.addViewController("/{_:^(?!api$).+}/**")
                .setViewName("forward:/index.html");
    }
}
