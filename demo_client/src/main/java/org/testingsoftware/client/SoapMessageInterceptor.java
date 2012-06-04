package org.testingsoftware.client;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.util.Assert;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.Document;

public class SoapMessageInterceptor implements ClientInterceptor {

	private final String message;

	public SoapMessageInterceptor(String message) {
		this.message = message;
	}

	public Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(new ByteArrayInputStream(xml.getBytes()));
	}

	public boolean handleRequest(MessageContext messageContext)
			throws WebServiceClientException {
		Assert.isInstanceOf(SoapMessage.class, messageContext.getRequest());
		SoapMessage msg = (SoapMessage) messageContext.getRequest();

		Document doc;
		
		try {
			doc = loadXMLFromString(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		msg.setDocument(doc);
		return true;
	}

	public boolean handleResponse(MessageContext messageContext)
			throws WebServiceClientException {
		return true;
	}

	public boolean handleFault(MessageContext messageContext)
			throws WebServiceClientException {
		return true;
	}

}
