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

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author GShokar
 */
@Path("/")
public class AuthenticationService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AuthenticationService
     */
    public AuthenticationService() {
        
    }

    /**
     * Retrieves representation of an instance of ca.aatl.app.invoicebook.bl.ws.AuthenticationService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user/checkSession")
    public String isSessionValid() {
        return "{\"sessionValid\": \"true\"}";
    }

    /**
     * PUT method for updating or creating an instance of AuthenticationService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("service/authenticate")
    public String authenticate(String request){
        //ObjectMapper mapper = new ObjectMapper();
        
        return "{\"status\":\"success\"}";
    }
}
