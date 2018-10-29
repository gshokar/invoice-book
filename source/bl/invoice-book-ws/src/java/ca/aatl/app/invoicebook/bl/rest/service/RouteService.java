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
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponseStatusEnum;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 * 
 * @author gshokar
 */

@Path("/")
public class RouteService {
    
    @Context
    private UriInfo context;
    
    private ServiceRequest serviceRequest;
    private final ServiceResponse serviceResponse;

    public RouteService() {
        this.serviceResponse = new ServiceResponse();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("service")
    public String route(String request) {
        
        Gson gson = new Gson();        

        try {
            serviceRequest = gson.fromJson(request, ServiceRequest.class);
            
        } catch (JsonSyntaxException ex) {
            serviceResponse.setStatus(ServiceResponseStatusEnum.Failed);
            serviceResponse.setMessage("Error: Invalid Service Request - " + ex.getMessage() );
            //TODO: log the error message
        }
        
        ResponseService service = getService();
        
        service.processRequest();
        
        return gson.toJson(serviceResponse);
    
    }
    
    private ResponseService getService() {
        
        ResponseService service = null;
        
        switch(serviceRequest.getRequestType()){
            case Authenticate:
                service = new AuthenticationService(serviceRequest, serviceResponse);
                break;
        }
        
        return service;
    }
}
