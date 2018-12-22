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

import ca.aatl.app.invoicebook.bl.ejb.SessionService;
import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequest;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.AppSession;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author gshokar
 */
public class ResponseService extends RestService {

    @Context
    protected HttpServletRequest httpRequest;
    
    @Context
    protected HttpHeaders httpHeaders;
    
    @EJB
    private SessionService sessionService;
    
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
    private AppSession session;

    /**
     * Get the value of session
     *
     * @return the value of session
     */
    public AppSession getSession() {
        
        if(session == null){
            session =  sessionService.find(httpHeaders.getHeaderString("sessionId"));
        }
        return session;
    }

    /**
     * Set the value of session
     *
     * @param session new value of session
     */
    public void setSession(AppSession session) {
        this.session = session;
    }

    public String getResponseJson(){
        return getGson().toJson(getResponse());
    }
}
