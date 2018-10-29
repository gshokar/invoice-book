/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequest;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponseStatusEnum;
import com.google.gson.Gson;
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
            
        } catch (Exception ex) {
            serviceResponse.setStatus(ServiceResponseStatusEnum.Failed);
            serviceResponse.setMessage("Error: Invalid Service Request - " + ex.getMessage() );
            //TODO: log the error message
        }
        
        
        //serviceResponse.setStatus(ServiceResponseStatusEnum.Success);
        
        return gson.toJson(serviceResponse);
    
    }
}
