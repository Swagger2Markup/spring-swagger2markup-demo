package io.github.robwin.swagger2markup.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.ant;

/**
 * Project:   spring-swagger2markup-demo
 * @author Robert Winkler
 */
@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket spicaRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(spicaApiInfo())
                .select()
                .paths(ant("/api/**"))
                .build();
    }

    private ApiInfo spicaApiInfo() {
        return new ApiInfoBuilder()
                .title("Petstore API Title")
                .description("Petstore API Description")
                .contact("Petstore API Contact Email")
                .version("1.0.0")
                .build();
    }
}
