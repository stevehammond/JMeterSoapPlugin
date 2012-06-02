package org.testingsoftware.calc;

import org.testingsoftware.calc.AddRequest;
import org.testingsoftware.calc.AddResponse;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

@Endpoint
public class CalcService {

    @PayloadRoot(localPart="plusRequest",  namespace="http://www.testing-software.org/calc")
    public AddResponse add(AddRequest data) {
        AddResponse result = new AddResponse();
        result.setResult(data.getA().add(data.getB()) );
        return result;
    }
}