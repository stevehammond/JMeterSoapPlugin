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

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;

import java.io.IOException;
import java.util.List;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.save.CSVSaveService;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;

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


    public SampleResult sample(Entry e) {
        log.debug("sampling");
        
        SampleResult res = new SampleResult();
        res.setSampleLabel(getName());
        res.setSamplerData(toString());
        res.setDataType(SampleResult.TEXT);
        // Bug 31184 - make sure encoding is specified
        //res.setDataEncoding(System.getProperty("file.encoding"));

        // Assume we will be successful
        res.setSuccessful(true);

        res.sampleStart();
        res.setResponseData("say hi from soap plugin");

        res.sampleEnd();
        return res;

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
