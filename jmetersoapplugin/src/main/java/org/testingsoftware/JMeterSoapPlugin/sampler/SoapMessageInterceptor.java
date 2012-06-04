package org.testingsoftware.JMeterSoapPlugin.sampler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.util.Assert;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SoapMessageInterceptor implements ClientInterceptor {

	private final String message;

	public SoapMessageInterceptor(String message) {
		this.message = message;
	}

	public boolean handleRequest(MessageContext messageContext)
			throws WebServiceClientException {
		Assert.isInstanceOf(SoapMessage.class, messageContext.getRequest());
		SoapMessage msg = (SoapMessage) messageContext.getRequest();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);
		Document doc;
		try {
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new ByteArrayInputStream(message.getBytes()));
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
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
