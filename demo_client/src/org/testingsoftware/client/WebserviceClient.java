package org.testingsoftware.client;

import java.io.StringReader;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Source;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;
import org.springframework.ws.client.core.WebServiceTemplate;

public class WebserviceClient {

    private static final String MESSAGE = "      <calc:addRequest xmlns:calc=\"http://www.testing-software.org/calc\" >"
        + "         <calc:a>20</calc:a>"
        + "         <calc:b>10</calc:b>"
        + "      </calc:addRequest>";

    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println("does not process any arguments!");
            System.exit(2);
        }
        WebserviceClient.execute(MESSAGE);
    }

    private static void execute(String msg) {

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

        StreamSource source = new StreamSource(new StringReader(msg));
        StreamResult result = new StreamResult(System.out);

        webServiceTemplate.sendSourceAndReceiveToResult(
            "http://localhost:8080/demo_server.0.1/calc-service",
            source, result);
    }
}
