package com.emf.ingestion.validation;

import com.emf.common.model.RawTelemetry;
import com.emf.ingestion.error.SchemaValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import org.springframework.stereotype.Service;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Set;

@Service
public class SchemaValidationService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();
    private final JsonSchema jsonSchema;
    private final javax.xml.validation.Schema xsdSchema;

    public SchemaValidationService() {
        try {
            // Load JSON Schema from classpath (comes from common-models resources)
            try (InputStream in = getClass().getClassLoader()
                    .getResourceAsStream("schemas/telemetry-schema.json")) {
                if (in == null) throw new IllegalStateException("Missing telemetry-schema.json on classpath");
                JsonNode schemaNode = objectMapper.readTree(in);
                JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
                this.jsonSchema = factory.getSchema(schemaNode);
            }

            // Load XSD from classpath
            try (InputStream xin = getClass().getClassLoader()
                    .getResourceAsStream("schemas/telemetry.xsd")) {
                if (xin == null) throw new IllegalStateException("Missing telemetry.xsd on classpath");
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                this.xsdSchema = sf.newSchema(new StreamSource(xin));
            }

        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize schema validators", e);
        }
    }

    public void validateJson(RawTelemetry raw) {
        JsonNode node = objectMapper.valueToTree(raw);
        Set<ValidationMessage> errors = jsonSchema.validate(node);
        if (!errors.isEmpty()) {
            String msg = errors.iterator().next().getMessage(); // first error is enough for client
            throw new SchemaValidationException("JSON schema validation failed: " + msg);
        }
    }

    public void validateXml(RawTelemetry raw) {
        try {
            // Convert object -> XML string and validate against XSD
            String xml = xmlMapper.writeValueAsString(raw)
                    .replaceFirst("^<RawTelemetry>", "<telemetry>")
                    .replaceFirst("</RawTelemetry>$", "</telemetry>");
            javax.xml.validation.Validator v = xsdSchema.newValidator();
            v.validate(new StreamSource(new StringReader(xml)));
        } catch (SchemaValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new SchemaValidationException("XML schema validation failed: " + e.getMessage());
        }
    }
}
