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
import javax.ejb.EJB;
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

    @EJB
    private AuthenticationResponseService authService;

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
            setResponseError("Error: Invalid Request - " + ex.getMessage());
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex);
        }

        processRequest();

        return getGson().toJson(getResponse());

    }

    private void processRequest() {
        try {

            if (isRequestAuthenticated()) {

                ResponseService service = getService();

                service.setRequest(getRequest());
                service.setResponse(getResponse());

                service.processRequest();

            } else {
                setResponseError("Invalid Request - session expired, please login.");
            }

        } catch (Exception ex) {
            setResponseError("Error: Failed to process the request - " + ex.getMessage());
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ResponseService getService() throws Exception {

        ResponseService service = null;

        if (getRequest().getRequestType() == ServiceRequestTypeEnum.Authenticate) {

            service = authService;

        } else {

            service = ServiceHandler.getInstance().getService(getRequest().getDataType());

            // AuthService have override method getSession to set the session from SessionService
            service.setSession(authService.getSession());
        }

        return service;
    }

    private boolean isRequestAuthenticated() throws NamingException {

        boolean authenticated = true;

        if (getRequest().getRequestType() != ServiceRequestTypeEnum.Authenticate) {

            authService.setRequest(getRequest());

            authenticated = authService.isValidRequest();
        }

        return authenticated;
    }
}
