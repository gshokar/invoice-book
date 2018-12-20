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
import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequestTypeEnum;
import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.AddressDto;
import ca.aatl.app.invoicebook.dto.ClientDto;
import ca.aatl.app.invoicebook.dto.ClientSearchDto;
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

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
public class ClientResponseService extends ResponseService {

    @EJB
    ClientService clientService;

    @EJB
    MappingService mappingService;

    @Override
    public void processRequest() {

        try {

            switch (getRequest().getDataType()) {
                case ClientSearch:
                    find();
                    break;
                case Client:

                    clientAction();

                    break;

            }

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResponseService.class.getName()).log(Level.SEVERE, "System error ClientResponseService processRequest", ex);
        }
    }

    private void clientAction() throws Exception {

        if (getRequest().getRequestType() == ServiceRequestTypeEnum.Get) {
            getClient();
        } else {
            update();
        }
    }

    private void find() throws Exception {

        try {
            ClientSearchDto searchDto = getDto(ClientSearchDto.class);

            List<ClientDto> dtoClients = new ArrayList<>();

            if (searchDto.getNumber() != null && !searchDto.getNumber().trim().isEmpty()) {

                Client client = clientService.find(searchDto.getNumber());

                if (client != null) {

                    ClientDto dto = newClientDto();

                    mappingService.updateClientDto(dto, client);

                    dtoClients.add(dto);

                } else {
                    addWarningMessage("Client number " + searchDto.getNumber() + " do not exists.");
                }

            } else {

                List<Client> clients = clientService.find(searchDto.getName(), searchDto.getPhone());

                if (clients.isEmpty()) {
                    addWarningMessage("No client record found");
                } else {
                    clients.forEach(c -> {

                        ClientDto dto = newClientDto();

                        mappingService.updateClientDto(dto, c);

                        dtoClients.add(dto);

                    });
                }
            }

            setResponseSuccess(dtoClients);

        } catch (JsonSyntaxException ex) {

            setResponseError("Invalid client search data - " + ex.getMessage());

            Logger.getLogger(ClientResponseService.class.getName()).log(Level.INFO, "Invalid ClientSearchDto Json", ex);
        }
    }

    private void update() throws Exception {

        try {

            ClientDto clientDto = getDto(ClientDto.class);

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

            Logger.getLogger(ClientResponseService.class.getName()).log(Level.INFO, "Invalid ClientDto Json for update", ex);

        } catch (DataValidationException ex) {

            setResponseError(ex.getValidationMessage());

            Logger.getLogger(ClientResponseService.class.getName()).log(Level.INFO, "Invalid ClientDto data for update", ex);

        }

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

    private void getClient() throws Exception {
        try {
            ClientSearchDto searchDto = getDto(ClientSearchDto.class);

            ClientDto dto = newClientDto();

            if (searchDto.getNumber() != null && !searchDto.getNumber().trim().isEmpty()) {

                Client client = clientService.find(searchDto.getNumber());

                if (client != null) {

                    mappingService.updateClientDto(dto, client);

                    setResponseSuccess(dto);

                } else {
                    addWarningMessage("Client number " + searchDto.getNumber() + " do not exists.");
                }

            } else {
                setResponseError("Invalid client number");
            }
        } catch (JsonSyntaxException ex) {

            setResponseError("Invalid client number - " + ex.getMessage());

            Logger.getLogger(ClientResponseService.class.getName()).log(Level.INFO, "Invalid ClientSearchDto Json", ex);
        }
    }
}
