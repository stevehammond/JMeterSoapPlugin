package org.testingsoftware.calc;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.WebServiceValidationException;
import org.springframework.ws.soap.client.SoapFaultClientException;

import org.testingsoftware.calc.AddRequest;
import org.testingsoftware.calc.AddResponse;
import org.testingsoftware.calc.ObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-config.xml")
public class CalcServiceTest {

	@Autowired
	@Qualifier("webServiceTemplate")
	private WebServiceTemplate webServiceTemplate;

	// @Autowired
	// @Qualifier("webServiceTemplateWithClientValidator")
	// private WebServiceTemplate webServiceTemplateWithClientValidator;

	@Test
	public void testCalcService() {

		AddRequest request = new ObjectFactory().createAddRequest();
		request.setA(BigInteger.valueOf(10));
		request.setB(BigInteger.valueOf(20));
		AddResponse response = (AddResponse) webServiceTemplate
				.marshalSendAndReceive(request);

		assertEquals(BigInteger.valueOf(30), response.getResult());
	}

/*
	@Test(expected = SoapFaultClientException.class)
	public void testCalcServiceWithTooLongAccountNumber() {

		CalcRequest request = new CalcRequest();
		request.setName("yyy");
		request.setNumber("xxx");

		webServiceTemplate.marshalSendAndReceive(request);
	}

	@Test(expected = SoapFaultClientException.class)
	public void testCalcServiceWithInvalidAccountNumber() {

		CalcRequest request = new CalcRequest();
		request.setName("dd");
		request.setNumber("ccc");

		webServiceTemplate.marshalSendAndReceive(request);
	}

	@Test
	public void testCalcServiceWithClientValidator() {

		CalcRequest request = new ObjectFactory()
				.createCalcRequest(); 
		request.setName("dd");
		request.setNumber("dd");
		CalcResponse response = (CalcResponse) webServiceTemplateWithClientValidator
				.marshalSendAndReceive(request);

		assertEquals(BigDecimal.valueOf(100.5), response.getBalance());
	}

	@Test(expected = WebServiceValidationException.class)
	public void testCalcServiceWithClientValidatorAndTooLongAccountNumber() {

		CalcRequest request = new ObjectFactory()
				.createCalcRequest(); 
		request.setName("ccc");
		request.setNumber("ccc");

		webServiceTemplateWithClientValidator.marshalSendAndReceive(request);

	}
	*/
}
