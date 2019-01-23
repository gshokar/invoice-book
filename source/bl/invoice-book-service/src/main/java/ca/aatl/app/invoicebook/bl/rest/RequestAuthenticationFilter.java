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

import ca.aatl.app.invoicebook.bl.ejb.SessionService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author GShokar
 */

@PreMatching
@Provider
public class RequestAuthenticationFilter extends AuthenticationFilter {

    SessionService sessionService;

    @Override
    public void filter(ContainerRequestContext crContext) throws IOException {

        String sessionId = crContext.getHeaderString("sessionId");

        try {
            if (isValidSession(sessionId)) {
               
                crContext.setSecurityContext(new AppSecurity(new AppUserPrincipal(getSessionService().find(sessionId)), crContext.getUriInfo()));
            } 
            
        } catch (Exception ex) {

            crContext.abortWith(getResponse("Request authentication process failed - server error"));

            Logger.getLogger(AuthenticatingFilter.class.getName()).log(Level.SEVERE, "System error request authentication process failed", ex);
        }

    }

    public boolean isValidSession(String sessionId) throws NamingException {

        return sessionId != null && getSessionService().isExists(sessionId);
    }

    private SessionService getSessionService() throws NamingException {

        if (sessionService == null) {

            sessionService = SessionService.getIntsance();
        }
        return sessionService;
    }

    
}
