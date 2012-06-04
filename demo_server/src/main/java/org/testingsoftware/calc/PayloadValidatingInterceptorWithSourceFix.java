package org.testingsoftware.calc;

import java.io.StringReader;
import java.io.StringWriter;

import org.springframework.ws.client.WebServiceTransformerException;
import org.springframework.ws.client.support.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.WebServiceMessage;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

public class PayloadValidatingInterceptorWithSourceFix extends
        PayloadValidatingInterceptor {
 
    @Override
    protected Source getValidationRequestSource(WebServiceMessage request) {
        return transformSourceToStreamSourceWithStringReader(request.getPayloadSource());
    }
 
    @Override
    protected Source getValidationResponseSource(WebServiceMessage response) {
        return transformSourceToStreamSourceWithStringReader(response.getPayloadSource());
    }
 
    Source transformSourceToStreamSourceWithStringReader(Source notValidatableSource) {
        final Source source;
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
 
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                    "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            StringWriter writer = new StringWriter();
            transformer.transform(notValidatableSource, new StreamResult(
                    writer));
 
            String transformed = writer.toString();
            StringReader reader = new StringReader(transformed);
            source = new StreamSource(reader);
 
        } catch (TransformerException transformerException) {
            throw new WebServiceTransformerException(
                    "Could not convert the source to a StreamSource with a StringReader",
                    transformerException);
        }
 
        return source;
    }
}
