/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-26  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequest;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponseStatusEnum;
import ca.aatl.app.invoicebook.dto.AuthenticateDto;
import com.google.gson.Gson;

/**
 *
 * @author GShokar
 */

public class AuthenticationService extends ResponseService{

    public AuthenticationService(ServiceRequest request, ServiceResponse response) {
        super(request, response);
    }
    
    public void authenticate() {

        Gson gson = new Gson();
        
        try {

            AuthenticateDto dto = gson.fromJson(getRequest().getData(), AuthenticateDto.class);
            
        } catch (Exception ex) {
            getResponse().setStatus(ServiceResponseStatusEnum.Failed);
            getResponse().setMessage("Error: Invalid authentication data - " + ex.getMessage() );
            //TODO: log the error message
        }
        
        getResponse().setStatus(ServiceResponseStatusEnum.Success);
        
        //return gson.toJson(serviceResponse);
    }
}
