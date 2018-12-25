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

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author GShokar
 */
public class AppSecurity implements SecurityContext{

    public static final String ROLE_ADMIN = "ADMIN";
    
    private AppUserPrincipal user;
    
    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public AppUserPrincipal getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(AppUserPrincipal user) {
        this.user = user;
    }

    public AppSecurity(AppUserPrincipal user, UriInfo uriInfo) {
        this.user = user;
        this.uriInfo = uriInfo;
    }

    private UriInfo uriInfo;

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return ROLE_ADMIN.equals(role);
    }

    @Override
    public boolean isSecure() {
        return uriInfo != null && uriInfo.getAbsolutePath().toString().startsWith("https");
    }

    @Override
    public String getAuthenticationScheme() {
        return "Token-Based-Auth-Scheme";
    }
    
}
