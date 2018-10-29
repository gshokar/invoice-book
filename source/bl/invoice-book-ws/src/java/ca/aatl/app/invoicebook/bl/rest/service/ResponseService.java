/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequest;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;

/**
 *
 * @author gshokar
 */
public abstract class ResponseService {
 
    private final ServiceRequest request;
    private final ServiceResponse response;

    public ServiceRequest getRequest() {
        return request;
    }

    public ServiceResponse getResponse() {
        return response;
    }

    public ResponseService(ServiceRequest request, ServiceResponse response) {
        this.request = request;
        this.response = response;
    }
    
    
}
