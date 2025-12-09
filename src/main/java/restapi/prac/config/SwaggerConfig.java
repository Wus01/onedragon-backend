package restapi.prac.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("일용이네 API 문서")       //문서 제목
                        .version("v1.0.0")               // 버전
                        .description("일용이네 서비스 API 명세서입니다. 멜렁") // 설명
                );
    }
}
