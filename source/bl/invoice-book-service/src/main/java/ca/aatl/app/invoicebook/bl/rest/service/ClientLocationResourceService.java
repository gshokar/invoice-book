/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-09  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.ClientLocationService;
import ca.aatl.app.invoicebook.bl.ejb.ClientService;
import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.bl.rest.ResourceResponseInitiated;
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientLocation;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.AddressDto;
import ca.aatl.app.invoicebook.dto.ClientLocationDto;
import ca.aatl.app.invoicebook.dto.ContactDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * REST Web Service
 *
 * @author GShokar
 */
@Stateless
@LocalBean
@Path("client/location")
public class ClientLocationResourceService extends ResponseService {

    @EJB
    ClientLocationService clientLocationService;

    @EJB
    ClientService clientService;
    
    @EJB
    MappingService mappingService;

    /**
     * Creates a new instance of ClientLocationResourceService
     */
    public ClientLocationResourceService() {
    }

    /**
     * Retrieves representation of an instance of
     * ca.aatl.app.invoicebook.bl.rest.service.ClientLocationResourceService
     *
     * @param clientNumber
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/list/{clientNumber}")
    @Authenticated
    @ResourceResponseInitiated
    public String getList(@PathParam("clientNumber") String clientNumber) {

        try {

           
            List<ClientLocationDto> dtoLocations = new ArrayList<>();

            List<ClientLocation> locations = clientLocationService.list(clientNumber);

            if (locations.isEmpty()) {
                addWarningMessage("No client locations yet");
            } else {
                locations.forEach(l -> {

                    ClientLocationDto dto = newClientLocationDto();

                    mappingService.updateClientLocationDto(dto, l);

                    dtoLocations.add(dto);

                });
            }

            setResponseSuccess(dtoLocations);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientLocationResourceService.class.getName()).log(Level.SEVERE, "System error ClientLocationResourceService getList", ex);
        }
        return getResponseJson();
    }

    /**
     * PUT method for updating or creating an instance of
     * ClientLocationResourceService
     *
     * @param sc
     * @param json representation for the resource
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    @ResourceResponseInitiated
    public String save(@Context SecurityContext sc, String json) {

        try {
           
            ClientLocationDto dto = getDto(ClientLocationDto.class, json);

            validate(dto);
            
            ClientLocation clientLocation = null;
            
            if (dto.getNumber() != null && dto.getNumber().trim().length() == 0) {

                clientLocation = clientLocationService.newEntity();

                clientLocation.setClient(getClient(dto.getClientNumber()));
                
            } else {
                clientLocation = clientLocationService.find(dto.getNumber());
                
                if(clientLocation == null){
                    throw new DataValidationException("Invalid client location number do not exists.");
                }
            }
                        
            mappingService.updateClientLocation(clientLocation, dto);

            clientLocation.setLastUpdatedBy(getUserId(sc));
            clientLocation.setLastUpdatedDate(Calendar.getInstance().getTime());

            clientLocationService.save(clientLocation);

            mappingService.updateClientLocationDto(dto, clientLocation);

            setResponseSuccess(dto);
            
        } catch (JsonSyntaxException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid client location data - " + ex.getMessage());

            Logger.getLogger(ClientLocationResourceService.class.getName()).log(Level.INFO, "Invalid ClientLocationDto Json for update", ex);

        } catch (DataValidationException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, ex.getValidationMessage());

            Logger.getLogger(ClientLocationResourceService.class.getName()).log(Level.INFO, "Invalid ClientLocationDto data for update", ex);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientLocationResourceService.class.getName()).log(Level.SEVERE, "System error ClientLocationResourceService save", ex);
        }
        return getResponseJson();
    }

    private ClientLocationDto newClientLocationDto() {

        ClientLocationDto dto = new ClientLocationDto();

        dto.setAddress(new AddressDto());
        dto.setContact(new ContactDto());
        return dto;
    }

    private void validate(ClientLocationDto clientLocationDto) throws Exception {
        
        // This is workaround for now
        // There should be dto validations
        // There should be bean validations too
        ClientLocation clientLocation = clientLocationService.newEntity();
        
        mappingService.updateClientLocation(clientLocation, clientLocationDto);
        
        clientLocation.setNumber(clientLocationDto.getNumber());
                
        clientLocation.setClient(getClient(clientLocationDto.getClientNumber()));
        
        clientLocationService.validate(clientLocation);
    }

    private Client getClient(String clientNumber) throws DataValidationException, Exception {
        
        Client client = clientService.find(clientNumber);
        
        if(client == null){
            throw new DataValidationException("Invalid client number provieded in client location request.");
        }
        return client;
    }
}
