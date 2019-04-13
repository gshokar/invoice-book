/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-09  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.ClientService;
import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.AddressDto;
import ca.aatl.app.invoicebook.dto.ClientDto;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.bl.rest.ResourceResponseInitiated;
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
@Path("clients")
//@DeclareRoles(AppSecurity.ROLE_ADMIN)
public class ClientResourceService extends ResponseService {

    @EJB
    ClientService clientService;

    @EJB
    MappingService mappingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/find")
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String find(
            @QueryParam("name") String name,
            @QueryParam("phone") String phone) {

        try {

            List<ClientDto> dtoClients = new ArrayList<>();

            List<Client> clients = clientService.find(name, phone);

            if (clients.isEmpty()) {
                addWarningMessage("No client record found");
            } else {
                clients.forEach(c -> {

                    ClientDto dto = newClientDto();

                    mappingService.updateClientDto(dto, c);

                    dtoClients.add(dto);

                });
            }

            setResponseSuccess(dtoClients);

        } catch (JsonSyntaxException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid client search data - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid ClientSearchDto Json", ex);
        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.SEVERE, "System error ClientResponseService find", ex);
        }

        return getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{number}")
    @Authenticated
    public String getClient(@PathParam("number") String number) {
        try {

            ClientDto dto = getDto(number);

            setResponseSuccess(dto);

        } catch (DataValidationException dex) {
            setResponseError(ErrorResponse.CODE_BAD_REQUEST, dex.getValidationMessage());
        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.SEVERE, "System error ClientResponseService getClient", ex);
        }

        return getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String list() {
        try {
            List<ClientDto> dtoClients = new ArrayList<>();

            List<Client> clients = clientService.list();

            if (clients.isEmpty()) {
                addWarningMessage("No client record found");
            } else {
                clients.forEach(c -> {

                    ClientDto dto = newClientDto();

                    mappingService.updateClientDto(dto, c);

                    dtoClients.add(dto);

                });
            }

            setResponseSuccess(dtoClients);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.SEVERE, "System error ClientResponseService list", ex);
        }

        return getResponseJson();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    @ResourceResponseInitiated
    public String save(@Context SecurityContext sc, String json) {

        try {

            ClientDto clientDto = getDto(ClientDto.class, json);

            validate(clientDto);

            Client client = null;

            if (clientDto.getNumber() != null && clientDto.getNumber().trim().length() == 0) {

                client = clientService.newEntity();

            } else {
                client = clientService.find(clientDto.getNumber());

                if (client == null) {
                    throw new DataValidationException("Invalid client number do not exists.");
                }
            }

            mappingService.updateClient(client, clientDto);

            client.setLastUpdatedBy(getUserId(sc));
            client.setLastUpdatedDate(Calendar.getInstance().getTime());

            clientService.save(client);

            mappingService.updateClientDto(clientDto, client);

            setResponseSuccess(clientDto);

        } catch (JsonSyntaxException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid client data - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid ClientDto Json for update", ex);

        } catch (DataValidationException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, ex.getValidationMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid ClientDto data for update", ex);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.SEVERE, "System error ClientResponseService update", ex);
        }

        return this.getResponseJson();
    }

    private ClientDto newClientDto() {
        ClientDto dto = new ClientDto();

        dto.setAddress(new AddressDto());
        dto.setContact(new ContactDto());
        return dto;
    }

    private void validate(ClientDto clientDto) throws Exception {

        // This is workaround for now
        // There should be dto validations
        // There should be bean validations too
        Client client = clientService.newEntity();

        mappingService.updateClient(client, clientDto);

        client.setNumber(clientDto.getNumber());
        clientService.validate(client);
    }

    public ClientDto getDto(String number) throws Exception {

        ClientDto dto = newClientDto();

        if (number != null && !number.trim().isEmpty()) {

            Client client = clientService.find(number);

            if (client != null) {

                mappingService.updateClientDto(dto, client);

            } else {
                throw new DataValidationException("Client number " + number + " do not exists.");
            }

        } else {
            throw new DataValidationException("Invalid client number");
        }

        return dto;
    }
}
