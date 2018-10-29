/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-28  GShokar         Created
 * =============================================================================
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

    public abstract void processRequest();
    
    
}
