package org.testingsoftware.client;

import java.io.StringReader;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.ws.security.WSConstants;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;

public class WebserviceClient {

//	private static final String MESSAGE = "      <calc:addRequest xmlns:calc=\"http://www.testing-software.org/calc\">"
//			+ "         <calc:a>10</calc:a>"
//			+ "         <calc:b>20</calc:b>"
//			+ "      </calc:addRequest>";

	//     <ns1:UserName xmlns:ns1="urn:thisNamespace">John Doe</ns1:UserName>

	
	private static final String MESSAGE = "<soapenv:Envelope "
			+ "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
			+ "xmlns:calc=\"http://www.testing-software.org/calc\">"
			+ "   <soapenv:Header>"
			+ "      <ns1:UserName xmlns:ns1=\"urn:thisNamespace\">John Doe</ns1:UserName>"
			+ "   </soapenv:Header>"
			+ "   <soapenv:Body>"
			+ "      <calc:addRequest>"
			+ "         <calc:a>10</calc:a>"
			+ "         <calc:b>20</calc:b>"
			+ "      </calc:addRequest>"
			+ "   </soapenv:Body>"
			+ "</soapenv:Envelope>";

	private static final String USERNAME = "ernie";

	private static final String PASSWORD = "secret";

	public static void main(String[] args) throws Exception {
		Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
		securityInterceptor.setSecurementActions("UsernameToken");
		securityInterceptor.setSecurementUsername(USERNAME);
		securityInterceptor.setSecurementPassword(PASSWORD);
		securityInterceptor.setSecurementPasswordType(WSConstants.PW_DIGEST);
		securityInterceptor.afterPropertiesSet();
		
		SoapMessageInterceptor messageInterceptor = new SoapMessageInterceptor(MESSAGE);

		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate
				.setInterceptors(new ClientInterceptor[] { messageInterceptor, securityInterceptor });
		webServiceTemplate.afterPropertiesSet();

		StreamSource source = new StreamSource(new StringReader("<n/>"));
		StreamResult result = new StreamResult(System.out);

		webServiceTemplate.sendSourceAndReceiveToResult(
				"http://localhost:8080/demo_server/calc-service", source,
				result);
	}

}
