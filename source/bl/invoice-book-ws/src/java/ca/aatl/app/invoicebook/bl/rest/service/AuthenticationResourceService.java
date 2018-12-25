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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
@Path("login")
public class AuthenticationResourceService extends ResponseService {

    @EJB
    private UserService userService;

    @EJB
    private SessionService sessionService;

    @Context
    HttpServletRequest httpRequest;
            
    public AuthenticationResourceService() {
    }

    public AuthenticationResourceService(ServiceRequest request, ServiceResponse response) {
        super(request, response);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String login(String jsonData) {

        try {

            AuthenticateDto dto = getGson().fromJson(jsonData, AuthenticateDto.class);

            authenticate(dto.getLoginId(), dto.getPassword());

        } catch (JsonSyntaxException ex) {

            setResponseError("Invalid login data - " + ex.getMessage());

            Logger.getLogger(AuthenticationResourceService.class.getName()).log(Level.INFO, "Invalid AuthenticationDto Json", ex);
        } catch (Exception ex) {
            setResponseError("System error login failed - " + ex.getMessage());

            Logger.getLogger(AuthenticationResourceService.class.getName()).log(Level.SEVERE, "System error login failed", ex);
        }

        return getResponseJson();

    }

    private void authenticate(String loginId, String password) throws Exception {

        try {

            AppUser user = userService.validateLogin(loginId, password);

            SessionDto sessionDto = new SessionDto();

            sessionDto.setUserName(user.getName());
            sessionDto.setSessionId(sessionService.getSessionId(user, httpRequest.getRemoteAddr()));

            setResponseSuccess(sessionDto);

        } catch (DataValidationException ex) {

            setResponseError(ex.getValidationMessage());

        }
    }

    public boolean isValidRequest() {

        return sessionService.isExists(getRequest().getSessionId());
    }
}
