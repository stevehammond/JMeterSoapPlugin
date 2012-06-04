/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.testingsoftware.JMeterSoapPlugin.sampler;

import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.apache.ws.security.WSConstants;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
import org.springframework.xml.transform.StringResult;

/**
 *
 */
public class JMeterSoapSampler extends AbstractSampler 
    implements TestBean {
    private static final Logger log = LoggingManager.getLoggerForClass();

    private static final long serialVersionUID = 2323L;

    private transient String host;

    private transient String port;

    private transient String path;

    private transient String user;

    private transient String password;

    private transient String data;


    private String sendSoapMsg(String uri, String msg) {
        // assembles the soap message and sends it to webservice

        StreamSource source = new StreamSource(new StringReader("<n/>"));
        StringResult result = new StringResult();

        // add the security interceptor
		Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
		securityInterceptor.setSecurementActions("UsernameToken");
		securityInterceptor.setSecurementUsername(getUser());
		securityInterceptor.setSecurementPassword(getPassword());
		securityInterceptor.setSecurementPasswordType(WSConstants.PW_DIGEST);
		try {
			securityInterceptor.afterPropertiesSet();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		SoapMessageInterceptor messageInterceptor = new SoapMessageInterceptor(msg);

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate
				.setInterceptors(new ClientInterceptor[] { messageInterceptor, securityInterceptor });
		webServiceTemplate.afterPropertiesSet();

        webServiceTemplate.sendSourceAndReceiveToResult(uri, source, result);
        return result.toString();
    }


    public SampleResult sample(Entry e) {

        log.debug("sampling");
        
        SampleResult res = new SampleResult();
        res.setSampleLabel(getName());
        res.setSamplerData(toString());
        res.setDataType(SampleResult.TEXT);
        // Bug 31184 - make sure encoding is specified
        res.setDataEncoding("utf-8");

        res.sampleStart();

        String uri = "http://" + getHost() + ":" + getPort() + getPath();
        log.info("uri: " + uri);
        log.info("request" + getData());

        res.setSuccessful(true);
        String result;
        try {
        	result = sendSoapMsg(uri, getData());
            res.setResponseData(result);
        } catch (Exception e1) {
            res.setSuccessful(false);
            log.error("Exception while sending Soap request:\n" + e1);
            res.setResponseData("Exception while sending Soap request:\n" + e1);
        }

        res.sampleEnd();
        return res;
    }

    
    public String toString() {
        return getData();
    }


    // getters & setters
    public String getHost() {
        return host;
    }
 
    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }

    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

}
