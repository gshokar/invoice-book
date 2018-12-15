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

import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequest;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.AppSession;
import java.util.ArrayList;

/**
 *
 * @author gshokar
 */
public abstract class ResponseService extends RestService {

    public ResponseService() {
    }

    public ResponseService(ServiceRequest request, ServiceResponse response) {
        super(request, response);
    }

    public abstract void processRequest();

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

}
