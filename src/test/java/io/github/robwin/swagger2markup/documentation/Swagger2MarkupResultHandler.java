package io.github.robwin.swagger2markup.documentation;

import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import org.apache.commons.lang3.Validate;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Swagger2MarkupResultHandler implements ResultHandler {

    private static final String OUTPUT_DIR = "io.springfox.staticdocs.outputDir";

    private final String outputDir;
    private final MarkupLanguage markupLanguage;
    private final String examplesFolderPath;

    Swagger2MarkupResultHandler(String outputDir, MarkupLanguage markupLanguage, String examplesFolderPath) {
        this.outputDir = outputDir;
        this.markupLanguage = markupLanguage;
        this.examplesFolderPath = examplesFolderPath;
    }

    /**
     * Creates a Swagger2MarkupResultHandler.Builder
     *
     * @param outputDir the target folder
     * @return a Swagger2MarkupResultHandler.Builder
     */
    public static Builder convertIntoFolder(String outputDir){
        return new Builder(outputDir);
    }

    /**
     * Apply the action on the given result.
     *
     * @param result the result of the executed request
     * @throws Exception if a failure occurs
     */
    @Override
    public void handle(MvcResult result) throws Exception {
        String swaggerJson = result.getResponse().getContentAsString();
        Swagger2MarkupConverter.fromString(swaggerJson).withMarkupLanguage(markupLanguage)
                .withExamples(examplesFolderPath).build().intoFolder(outputDir);
    }


    public static class Builder{
        private final String outputDir;
        private String examplesFolderPath;
        private MarkupLanguage markupLanguage = MarkupLanguage.ASCIIDOC;

        Builder(String outputDir){
            this.outputDir = outputDir;
        }

        /**
         * Builds Swagger2MarkupResultHandler which converts the Swagger response into Markup and writes into the given {@code outputDir}.
         *
         * @return a Mock MVC {@code ResultHandler} that will produce the documentation
         * @see org.springframework.test.web.servlet.MockMvc#perform(org.springframework.test.web.servlet.RequestBuilder)
         * @see org.springframework.test.web.servlet.ResultActions#andDo(org.springframework.test.web.servlet.ResultHandler)
         */
        public Swagger2MarkupResultHandler build(){
            return new Swagger2MarkupResultHandler(new DocumentationProperties().getOutputDir() + "/" + outputDir, markupLanguage, examplesFolderPath);
        }

        /**
         * Specifies the markup language which should be used to generate the files
         *
         * @param markupLanguage the markup language which is used to generate the files
         * @return the Swagger2MarkupConverter.Builder
         */
        public Builder withMarkupLanguage(MarkupLanguage markupLanguage){
            this.markupLanguage = markupLanguage;
            return this;
        }

        /**
         * Include examples into the Paths document
         *
         * @param examplesFolderPath the path to the folder where the example documents reside
         * @return the Swagger2MarkupConverter.Builder
         */
        public Builder withExamples(String examplesFolderPath){
            this.examplesFolderPath = examplesFolderPath;
            return this;
        }
    }

    static class DocumentationProperties {
        private final Properties properties = new Properties();

        DocumentationProperties() {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(
                    "documentation.properties");
            if (stream != null) {
                try {
                    this.properties.load(stream);
                }
                catch (IOException ex) {
                    throw new IllegalStateException(
                            "Failed to read documentation.properties", ex);
                }
                finally {
                    try {
                        stream.close();
                    }
                    catch (IOException e) {
                        // Continue
                    }
                }
            }
            this.properties.putAll(System.getProperties());
        }

        String getOutputDir() {
            String outputDir = this.properties
                    .getProperty(OUTPUT_DIR);
            Validate.notEmpty(outputDir, "System property '" + OUTPUT_DIR + "' must not be empty!");
            return outputDir;
        }
    }
}
