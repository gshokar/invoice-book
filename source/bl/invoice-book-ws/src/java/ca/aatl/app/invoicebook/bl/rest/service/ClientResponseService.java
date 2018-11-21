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
import ca.aatl.app.invoicebook.dto.ClientSearchDto;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
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

    @Override
    public void processRequest() {

        switch (getRequest().getDataType()) {
            case ClientSearch:
                find();
                break;
            case Client:
                update();
                break;
        }
    }

    private void find() {

        try {
            ClientSearchDto searchDto = getDto(ClientSearchDto.class);
            
            List<Client> clients = new ArrayList<>();
            
            if(searchDto.getNumber() != null && !searchDto.getNumber().trim().isEmpty()){
                Client client = clientService.find(searchDto.getNumber());
                
                if(client != null){
                    clients.add(client);
                }else{
                    addWarningMessage("Client number " + searchDto.getNumber() + " do not exists.");
                }
                
            }else{
            
                clients = clientService.find(searchDto.getName(), searchDto.getPhone());
                
                if(clients.isEmpty()){
                    addWarningMessage("No client record found");
                }
            }

            setResponseSuccess(clients);
                
        } catch (JsonSyntaxException ex) {

            setResponseError("Invalid client search data - " + ex.getMessage());

            Logger.getLogger(ClientResponseService.class.getName()).log(Level.INFO, "Invalid ClientSearchDto Json", ex);
        }
    }

    private void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
