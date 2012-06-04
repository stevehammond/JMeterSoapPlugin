package org.testingsoftware.calc;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Ignore;
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
@Ignore
public class CalcServiceTest {

	@Autowired
	@Qualifier("webServiceTemplate")
	private WebServiceTemplate webServiceTemplate;

	@Autowired
	@Qualifier("webServiceTemplateWithClientValidator")
	private WebServiceTemplate webServiceTemplateWithClientValidator;

	@Test
	public void testCalcService() {

		AddRequest request = new ObjectFactory().createAddRequest();
		request.setA(BigInteger.valueOf(10));
		request.setB(BigInteger.valueOf(20));
		AddResponse response = (AddResponse) webServiceTemplate
				.marshalSendAndReceive(request);

		assertEquals(BigInteger.valueOf(30), response.getResult());
	}


	@Test(expected = SoapFaultClientException.class)
	public void testCalcServiceWithNegativeNumberArgument() {

		AddRequest request = new AddRequest();
		request.setA(BigInteger.valueOf(-1));
		request.setB(BigInteger.valueOf(20));
		AddResponse response = (AddResponse) webServiceTemplate
				.marshalSendAndReceive(request);
	}


	@Test
	public void testCalcServiceWithClientValidator() {

		AddRequest request = new ObjectFactory().createAddRequest();
		request.setA(BigInteger.valueOf(10));
		request.setB(BigInteger.valueOf(20));
		AddResponse response = (AddResponse) webServiceTemplate
				.marshalSendAndReceive(request);

		assertEquals(BigInteger.valueOf(30), response.getResult());
	}

	@Test(expected = WebServiceValidationException.class)
	public void testCalcServiceWithClientValidatorAndNegativeNumberArgument() {

		AddRequest request = new ObjectFactory().createAddRequest();
		request.setA(BigInteger.valueOf(-1));
		request.setB(BigInteger.valueOf(20));
		webServiceTemplateWithClientValidator.marshalSendAndReceive(request);
	}
}
