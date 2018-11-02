/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-26  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.SessionService;
import ca.aatl.app.invoicebook.bl.ejb.UserService;
import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequest;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.AppUser;
import ca.aatl.app.invoicebook.dto.AuthenticateDto;
import ca.aatl.app.invoicebook.dto.SessionDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import com.google.gson.JsonSyntaxException;
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
public class AuthenticationService extends ResponseService {

    @EJB
    private UserService userService;
    
    @EJB
    private SessionService sessionService;

    public AuthenticationService() {
    }

    public AuthenticationService(ServiceRequest request, ServiceResponse response) {
        super(request, response);
    }

    private void authenticate(String loginId, String password) throws Exception {

        try{
            
            AppUser user = userService.validateLogin(loginId, password);
            
            SessionDto sessionDto = new SessionDto();
                        
            sessionDto.setUserName(user.getName());
            sessionDto.setSessionId(sessionService.getSessionId(user, getRequest().getClientIP()));
            
            setResponseSuccess(sessionDto);
            
        }catch(DataValidationException ex){
            
            setResponseError(ex.getValidationMessage());
            
        }
    }

    @Override
    public void processRequest() {

        AuthenticateDto dto = null;

        try {

            dto = getGson().fromJson(getRequest().getData(), AuthenticateDto.class);

        } catch (JsonSyntaxException ex) {
            
            setResponseError("Invalid authentication data - " + ex.getMessage());
            
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.INFO, "Invalid AuthenticationDto Json", ex);
        }

        try {

            authenticate(dto.getLoginId(), dto.getPassword());

        } catch (Exception ex) {
            setResponseError("System error authentication failed - " + ex.getMessage());
            
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "System error authentication failed", ex);
        }
    }
}
