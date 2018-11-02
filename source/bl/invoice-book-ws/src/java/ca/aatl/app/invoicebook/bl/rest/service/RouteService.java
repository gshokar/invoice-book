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
import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequestTypeEnum;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import com.google.gson.JsonSyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author gshokar
 */
@Path("/")
public class RouteService extends RestService {

    @Context
    private HttpServletRequest httpRequest;

    public RouteService() {

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("service")
    public String route(String jsonRequest) {

        setResponse(new ServiceResponse());

        try {
            setRequest(getGson().fromJson(jsonRequest, ServiceRequest.class));
            
            getRequest().setClientIP(httpRequest.getRemoteAddr());
            
        } catch (JsonSyntaxException ex) {
            setResponseError("Error: Invalid Service Request - " + ex.getMessage());
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            ResponseService service = getService();

            service.processRequest();

        } catch (Exception ex) {
            setResponseError("Error: Failed to process the request - " + ex.getMessage());
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getGson().toJson(getResponse());

    }

    private ResponseService getService() throws NamingException {

        ResponseService service = null;

        if (getRequest().getRequestType() == ServiceRequestTypeEnum.Authenticate) {
            
            service = (ResponseService) InitialContext.doLookup("java:module/AuthenticationService");
            
        } else {
            
        }
        
        service.setRequest(getRequest());
        service.setResponse(getResponse());

        return service;
    }
}
