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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
@Path("client")
public class ClientResourceService extends ResponseService {

    @EJB
    ClientService clientService;

    @EJB
    MappingService mappingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/find")
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

            setResponseError("Invalid client search data - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid ClientSearchDto Json", ex);
        }catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.SEVERE, "System error ClientResponseService find", ex);
        }

        return getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{number}")
    public String getClient(@PathParam("number") String number) {
        try {

            ClientDto dto = newClientDto();

            if (number != null && !number.trim().isEmpty()) {

                Client client = clientService.find(number);

                if (client != null) {

                    mappingService.updateClientDto(dto, client);

                    setResponseSuccess(dto);

                } else {
                    addWarningMessage("Client number " + number + " do not exists.");
                }

            } else {
                setResponseError("Invalid client number");
            }
        } catch (JsonSyntaxException ex) {

            setResponseError("Invalid client number - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid ClientSearchDto Json", ex);
        }catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.SEVERE, "System error ClientResponseService getClient", ex);
        }

        return getResponseJson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String update(String json) {

        try {

            ClientDto clientDto = getDto(ClientDto.class, json);

            validate(clientDto);

            Client client = null;

            if (clientDto.getNumber() != null && clientDto.getNumber().trim().length() == 0) {

                client = clientService.newClient();

            } else {
                client = clientService.find(clientDto.getNumber());
            }

            mappingService.updateClient(client, clientDto);

            client.setLastUpdatedBy(getSession().getUser().getId());
            client.setLastUpdatedDate(Calendar.getInstance().getTime());

            clientService.save(client);

            mappingService.updateClientDto(clientDto, client);

            setResponseSuccess(clientDto);

        } catch (JsonSyntaxException ex) {

            setResponseError("Invalid client data - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid ClientDto Json for update", ex);

        } catch (DataValidationException ex) {

            setResponseError(ex.getValidationMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid ClientDto data for update", ex);

        }catch (Exception ex) {

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
        Client client = clientService.newClient();

        mappingService.updateClient(client, clientDto);

        clientService.validate(client);
    }
}
