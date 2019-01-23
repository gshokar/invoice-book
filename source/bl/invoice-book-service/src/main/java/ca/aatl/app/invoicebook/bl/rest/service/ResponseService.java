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

import ca.aatl.app.invoicebook.bl.rest.AppSecurity;
import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequest;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.AppSession;
import java.util.ArrayList;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author gshokar
 */
public class ResponseService extends RestService {
        

    public ResponseService() {
        this.response = new ServiceResponse();
    }

    public ResponseService(ServiceRequest request, ServiceResponse response) {
        super(request, response);
    }

    protected void addWarningMessage(String message) {

        if (getResponse().getWarningMessages() == null) {
            getResponse().setWarningMessages(new ArrayList<>());
        }

        getResponse().getWarningMessages().add(message);
    }

    public AppSession getSession(SecurityContext securityContext) {
        
        AppSession session = null;
        
        if(securityContext != null && securityContext instanceof AppSecurity){
            
            session = ((AppSecurity)securityContext).getUser().getSession();
        }
        return session;
    }

    public int getUserId(SecurityContext securityContext){
        
        return getSession(securityContext).getUser().getId();
    }
    
    public void clearWarningMessages() {

        if (getResponse().getWarningMessages() != null) {
            getResponse().getWarningMessages().clear();
        }

        
    }
}
