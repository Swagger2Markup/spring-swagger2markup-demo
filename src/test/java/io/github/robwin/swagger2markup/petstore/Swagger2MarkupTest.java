package io.github.robwin.swagger2markup.petstore;

import io.github.robwin.swagger2markup.documentation.Swagger2MarkupDocumentation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.restdocs.config.RestDocumentationConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.RestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class Swagger2MarkupTest {

    private static final Logger LOG = LoggerFactory.getLogger(Swagger2MarkupTest.class);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(new RestDocumentationConfigurer()).build();

        /*
        AbstractMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.context).
                apply(new RestDocumentationConfigurer());
        RestAssuredMockMvc.standaloneSetup(builder);
        */
    }

    @Test
    public void createAsciiDoc() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("get_swagger_doc"))
                .andDo(Swagger2MarkupDocumentation.document("swagger_adoc"))
                .andExpect(status().isOk());

        /*
        given().contentType(ContentType.JSON).resultHandlers(Swagger2MarkupDocumentation.document("get_swagger_doc")).
        when().get("/v2/api-docs").
        then().statusCode(200);
        */

    }

}
