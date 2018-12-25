/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-22  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author GShokar
 */

@Provider
@Authenticated
public class AuthenticatingFilter extends AuthenticationFilter {


    @Override
    public void filter(ContainerRequestContext crContext) throws IOException {

        try {
            if (!isAuthenticated(crContext.getSecurityContext())) {
                crContext.abortWith(getResponse("Request authentication failed"));
            }
        } catch (Exception ex) {

            crContext.abortWith(getResponse("Request authentication failed - server error"));

            Logger.getLogger(AuthenticatingFilter.class.getName()).log(Level.SEVERE, "System error request authentication failed", ex);
        }

    }

    private boolean isAuthenticated(SecurityContext securityContext) {
        return securityContext != null && securityContext.getUserPrincipal() != null;
    }

}
