/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-23  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.ClientLocationService;
import ca.aatl.app.invoicebook.bl.ejb.ClientService;
import ca.aatl.app.invoicebook.bl.ejb.TimeCodeService;
import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientLocation;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeCode;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.ClientDto;
import ca.aatl.app.invoicebook.dto.ClientLocationDto;
import ca.aatl.app.invoicebook.dto.TimeCodeDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
@Path("time-codes")
public class TimeCodeResourceService extends ResponseService {

    @EJB
    MappingService mappingService;

    @EJB
    TimeCodeService timeCodeService;

    @EJB
    ClientService clientService;

    @EJB
    ClientLocationService clientLocationService;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    public String save(@Context SecurityContext sc, String json) {

        try {

            TimeCodeDto dto = getDto(TimeCodeDto.class, json);

            validate(dto);

            TimeCode timeCode = null;

            if (AppUtils.isNullOrEmpty(dto.getUid())) {

                timeCode = timeCodeService.newEntity();

            } else {
                timeCode = timeCodeService.findByGuid(dto.getUid());

                if (timeCode == null) {
                    throw new DataValidationException("Invalid timeCode uid do not exists.");
                }
            }

            setClient(timeCode, dto.getClient());
            setClientLocation(timeCode, dto.getClientLocation());

            mappingService.updateTimeCode(timeCode, dto);

            timeCode.setLastUpdatedBy(getUserId(sc));
            timeCode.setLastUpdatedDate(Calendar.getInstance().getTime());

            timeCodeService.save(timeCode);

            mappingService.updateTimeCodeDto(dto, timeCode);

            setResponseSuccess(dto);

        } catch (JsonSyntaxException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid client data - " + ex.getMessage());

            Logger.getLogger(TimeCodeResourceService.class.getName()).log(Level.INFO, "Invalid ClientDto Json for update", ex);

        } catch (DataValidationException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, ex.getValidationMessage());

            Logger.getLogger(TimeCodeResourceService.class.getName()).log(Level.INFO, "Invalid ClientDto data for update", ex);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(TimeCodeResourceService.class.getName()).log(Level.SEVERE, "System error TimeCodeResourceService update", ex);
        }

        return this.getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String list() {
        try {
            List<TimeCodeDto> dtoList = getDtoList(timeCodeService.list());

            setResponseSuccess(dtoList);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(TimeCodeResourceService.class.getName()).log(Level.SEVERE, "System error TimeCodeResourceService list", ex);
        }

        return getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/find")
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String find(@QueryParam("clientNumber") String clientNumber) {
        
        try {
            
            List<TimeCodeDto> dtoList = getDtoList(timeCodeService.find(clientNumber));

            setResponseSuccess(dtoList);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(TimeCodeResourceService.class.getName()).log(Level.SEVERE, "System error TimeCodeResourceService find", ex);
        }

        return getResponseJson();
    }
    
    private List<TimeCodeDto> getDtoList(List<TimeCode> entityList) throws Exception {
        List<TimeCodeDto> dtoList = new ArrayList<>();
        
        if (entityList.isEmpty()) {
            addWarningMessage("No time code record found");
        } else {
            entityList.forEach(tc -> {
                
                TimeCodeDto dto = newTimeCodeDto();
                
                mappingService.updateTimeCodeDto(dto, tc);
                
                dtoList.add(dto);
                
            });
        }
        return dtoList;
    }
    private void setClientLocation(TimeCode timeCode, ClientLocationDto dto) throws Exception {

        if (dto != null && !AppUtils.isNullOrEmpty(dto.getNumber())) {

            ClientLocation location = clientLocationService.find(dto.getNumber());

            if (location == null) {
                throw new DataValidationException("Invalid client location number do not exists.");
            }

            if (!location.equals(timeCode.getClientLocation())) {

                timeCode.setClientLocation(location);
            }

        } else if (timeCode.getClientLocation() != null) {

            timeCode.setClientLocation(null);
        }
    }

    private void setClient(TimeCode timeCode, ClientDto dto) throws Exception {

        if (dto != null && !AppUtils.isNullOrEmpty(dto.getNumber())) {

            Client client = clientService.find(dto.getNumber());

            if (client == null) {
                throw new DataValidationException("Invalid client number do not exists.");
            }

            if (!client.equals(timeCode.getClient())) {

                timeCode.setClient(client);
            }

        } else if (timeCode.getClient() != null) {

            timeCode.setClient(null);
        }
    }

    private void validate(TimeCodeDto dto) throws Exception {
        // This is workaround for now
        // There should be dto validations
        // There should be bean validations too
        TimeCode timeCode = timeCodeService.newEntity();

        timeCode.setClient(new Client());
        timeCode.setClientLocation(new ClientLocation());

        mappingService.updateTimeCode(timeCode, dto);

        timeCode.setGuid(dto.getUid());

        timeCodeService.validate(timeCode);
    }

    private TimeCodeDto newTimeCodeDto() {
        TimeCodeDto timeCode = new TimeCodeDto();
        
        timeCode.setClient(new ClientDto());
        timeCode.setClientLocation(new ClientLocationDto());
        
        return timeCode;
    }
}
