package io.github.robwin.swagger2markup.demo;

import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import io.github.robwin.swagger2markup.builder.markup.MarkupLanguage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

/**
 * Project:   spring-swagger2markup-demo
 * @author Robert Winkler
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootSwaggerConfig.class)
@IntegrationTest
@WebAppConfiguration
public class Swagger2MarkupTest {

    @Test
    public void convertSwaggerToAsciiDoc() throws IOException {

        Swagger2MarkupConverter.from("http://localhost:8080/api-docs").
                withMarkupLanguage(MarkupLanguage.MARKDOWN).
                withExamples("docs/generated").withSchemas("docs/generated/schemas").build()
                .intoFolder("src/docs/markdown");

        Swagger2MarkupConverter.from("http://localhost:8080/api-docs").
                withExamples("docs/generated").withSchemas("docs/generated/schemas").build()
                .intoFolder("src/docs/asciidoc");
    }

}
