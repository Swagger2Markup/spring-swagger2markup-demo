package io.github.robwin.swagger2markup.demo.step_definitions;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.robwin.swagger2markup.demo.RestDocumented;
import io.github.robwin.swagger2markup.demo.SpringBootSwaggerConfig;
import io.github.robwin.swagger2markup.demo.model.MailStorageQuota;
import io.github.robwin.swagger2markup.demo.model.MailStorageQuotaResponse;
import io.github.robwin.swagger2markup.demo.model.MailStorageQuotaValue;
import io.github.robwin.swagger2markup.demo.model.QuotaValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.restdocs.core.RestDocumentationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.AbstractMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileNotFoundException;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.restdocs.core.RestDocumentation.document;

/**
 * @author Robert Winkler
 */
@WebAppConfiguration
@ContextConfiguration(classes = SpringBootSwaggerConfig.class, loader = SpringApplicationContextLoader.class)
public class QuotaSteps {

    private static final Logger LOG = LoggerFactory.getLogger(QuotaSteps.class);

    @Autowired
    private WebApplicationContext context;

    private RestDocumented restDocumented;

    @Before
    public void setUp() throws FileNotFoundException {
        AbstractMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.context).
                apply(new RestDocumentationConfiguration());
        RestAssuredMockMvc.standaloneSetup(builder);
        restDocumented = RestDocumented.fromProperties();
    }

    @When("^I create a mail storage quota with (\\-?\\d+) days$")
    public void I_create_a_mail_storage_quota_with_days(int days) throws Exception {
        MailStorageQuota storageQuota = new MailStorageQuota(MailStorageQuotaValue.valueOf(days), QuotaValueType.CUSTOM);

        given().contentType(ContentType.XML).body(storageQuota).resultHandlers(document("create_a_quota")).
        when().put("/quotas").
        then().statusCode(204);

        restDocumented.documentJsonSchema(MailStorageQuota.class, "schemas");
        restDocumented.documentXmlSchema(MailStorageQuota.class, "schemas");

        restDocumented.documentJsonSchema(MailStorageQuotaResponse.class, "schemas");
        restDocumented.documentXmlSchema(MailStorageQuotaResponse.class, "schemas");
    }

    @Then("^I expect that the mail storage quota is set to (\\-?\\d+) days and quota type is (.*)")
    public void I_expect_that_the_mail_storage_quota_is_set_to_days_and_quota_type_is_custom(int days, String type) throws Exception {

        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

}
