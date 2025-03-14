package dev.spring.petclinic.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("petclinic API 문서")
                        .version("v1")
                        .description("petclinic API 명세서입니다. 각 엔드포인트의 기능과 요청/응답 형식을 확인할 수 있습니다."));
    }
}
