/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-24  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest;

import ca.aatl.app.invoicebook.data.jpa.entity.AppSession;
import java.security.Principal;

/**
 *
 * @author GShokar
 */
public class AppUserPrincipal implements Principal{

    public AppUserPrincipal(AppSession session) {
        this.session = session;
    }

    @Override
    public String getName() {
        String name = null;
        
        if(session != null){
           name = session.getUser().getLoginId();
        }
        return name;
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
