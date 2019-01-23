/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-24  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest;

import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.bl.rest.service.RestService;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

/**
 *
 * @author GShokar
 */
public abstract class AuthenticationFilter implements ContainerRequestFilter {

    protected Response getResponse(int code, String message) {

        ServiceResponse sResponse = new ServiceResponse();

        RestService rService = new RestService();

        rService.setResponse(sResponse);
        rService.setResponseError(code, message);

        Response.ResponseBuilder rb = Response.status(Response.Status.OK).entity(rService.getResponseJson());

        Response response = rb.build();

        return response;
    }
       
    protected Response getResponse(String message) {
        
        return this.getResponse(ErrorResponse.CODE_INTERNAL_SERVER_ERROR, message);
    }
}
