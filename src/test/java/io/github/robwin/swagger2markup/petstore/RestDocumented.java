package io.github.robwin.swagger2markup.petstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *@author Robert Winkler
 */
public class RestDocumented {

    private static final Logger LOG = LoggerFactory.getLogger(RestDocumented.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The outputDir where the files are stored
     */
    private final String outputDir;

    private RestDocumented(){
        outputDir = new DocumentationProperties().getOutputDir();
    }

    public RestDocumented(String outputDir){
        this.outputDir = outputDir;
    }

    public static RestDocumented fromProperties() {
        return new RestDocumented();
    }

    /**
     * Creates a JSON Schema (XSD) file from a class. The class must have Jackson annotations.
     *
     * @param documentedClass the Class for which JSON schema should be generated
     * @param subDir a sub directory
     * @throws IOException
     */
    public <T> void documentJsonSchema(Class<T> documentedClass, String subDir) throws IOException {
        Files.createDirectories(Paths.get(outputDir, subDir));
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        objectMapper.acceptJsonFormatVisitor(objectMapper.constructType(documentedClass), visitor);
        JsonSchema jsonSchema = visitor.finalSchema();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, subDir, documentedClass.getSimpleName() + ".json"),
                StandardCharsets.UTF_8)){
            writer.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
        }
    }

    /**
     * Creates a JSON Schema (XSD) file from a class. The class must have Jackson annotations.
     *
     * @param documentedClass the Class for which JSON schema should be generated
     * @throws IOException
     */
    public <T> void documentJsonSchema(Class<T> documentedClass) throws IOException {
        documentJsonSchema(documentedClass, "");
    }

    /**
     * Creates a XML Schema Definition (XSD) file from a class. The class must have JAXB annotations.
     *
     * @param documentedClass the Class for which XML schema should be generated
     * @param subDir a sub directory
     * @throws JAXBException
     * @throws IOException
     */
    public <T> void documentXmlSchema(Class<T> documentedClass, String subDir) throws JAXBException, IOException {
        Files.createDirectories(Paths.get(outputDir, subDir));
        JAXBContext jaxbContext = JAXBContext.newInstance(documentedClass);
        SchemaOutputResolver sor = new XMLSchemaOutputResolver<>(outputDir, subDir, documentedClass);
        jaxbContext.generateSchema(sor);
    }

    /**
     * Creates a XML Schema Definition (XSD) file from a class. The class must have JAXB annotations.
     *
     * @param documentedClass the Class for which XML schema should be generated
     * @throws JAXBException
     * @throws IOException
     */
    public <T> void documentXmlSchema(Class<T> documentedClass) throws JAXBException, IOException {
        documentXmlSchema(documentedClass, "");
    }

    static class XMLSchemaOutputResolver<T> extends SchemaOutputResolver {
        private final String outputDir;
        private final String subDir;
        private final Class<T> documentedClass;

        private XMLSchemaOutputResolver(String outputDir, String subDir, Class<T> documentedClass) {
            this.outputDir = outputDir;
            this.subDir = subDir;
            this.documentedClass = documentedClass;
        }

        public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
            return new StreamResult(Paths.get(outputDir, subDir, documentedClass.getSimpleName() + ".xsd").toFile());
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
                    .getProperty("io.restdocumented.outputDir");
            Validate.notEmpty(outputDir, "System property 'io.restdocumented.outputDir' must not be empty!");
            return outputDir;
        }
    }
}
