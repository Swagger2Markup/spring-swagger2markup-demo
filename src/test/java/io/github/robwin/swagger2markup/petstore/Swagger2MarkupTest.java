package io.github.robwin.swagger2markup.petstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.robwin.swagger2markup.documentation.Swagger2MarkupDocumentation;
import io.github.robwin.swagger2markup.petstore.model.Category;
import io.github.robwin.swagger2markup.petstore.model.Pet;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    }

    @Test
    public void createAsciiDoc() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("get_swagger_doc"))
                .andDo(Swagger2MarkupDocumentation.document("swagger_adoc"))
                .andExpect(status().isOk());

    }

    @Test
    public void findPetById() throws Exception {
        this.mockMvc.perform(post("/api/pet/").content(createPet())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("add_a_new_pet_to_the_store"))
                .andExpect(status().isOk());
    }

    private String createPet() throws JsonProcessingException {
        Pet pet = new Pet();
        pet.setId(1l);
        pet.setName("Wuffy");
        Category category = new Category(1l, "Hund");
        pet.setCategory(category);
        return new ObjectMapper().writeValueAsString(pet);
    }


}
