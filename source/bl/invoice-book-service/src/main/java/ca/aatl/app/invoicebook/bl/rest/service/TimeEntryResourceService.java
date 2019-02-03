/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-28  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.EmployeeService;
import ca.aatl.app.invoicebook.bl.ejb.TimeCodeService;
import ca.aatl.app.invoicebook.bl.ejb.TimeEntryService;
import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeCode;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeEntry;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.EmployeeDto;
import ca.aatl.app.invoicebook.dto.TimeCodeDto;
import ca.aatl.app.invoicebook.dto.TimeEntryDto;
import ca.aatl.app.invoicebook.dto.TimeSheetDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@Path("time-entries")
public class TimeEntryResourceService extends ResponseService {

    @EJB
    MappingService mappingService;

    @EJB
    TimeEntryService timeEntryService;
 
    @EJB
    TimeCodeService timeCodeService;
    
    @EJB
    EmployeeService employeeService;
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    public String save(@Context SecurityContext sc, String json) {

        try {

            TimeEntryDto dto = getDto(TimeEntryDto.class, json);

            validate(dto);

            TimeEntry timeEntry = null;

            if (AppUtils.isNullOrEmpty(dto.getUid())) {

                timeEntry = timeEntryService.newEntity();

            } else {
                timeEntry = timeEntryService.find(dto.getUid());

                if (timeEntry == null) {
                    throw new DataValidationException("Invalid timeEntry uid do not exists.");
                }
            }

            setTimeCode(timeEntry, dto.getTimeCode());
            setEmployee(timeEntry, dto.getEmployee());

            mappingService.updateTimeEntry(timeEntry, dto);

            timeEntry.setLastUpdatedBy(getUserId(sc));
            timeEntry.setLastUpdatedDate(Calendar.getInstance().getTime());
            
            timeEntryService.save(timeEntry);

            mappingService.updateTimeEntryDto(dto, timeEntry);

            setResponseSuccess(dto);

        } catch (JsonSyntaxException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid client data - " + ex.getMessage());

            Logger.getLogger(TimeEntryResourceService.class.getName()).log(Level.INFO, "Invalid ClientDto Json for update", ex);

        } catch (DataValidationException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, ex.getValidationMessage());

            Logger.getLogger(TimeEntryResourceService.class.getName()).log(Level.INFO, "Invalid ClientDto data for update", ex);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(TimeEntryResourceService.class.getName()).log(Level.SEVERE, "System error TimeCodeResourceService update", ex);
        }

        return this.getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String list() {
        try {
           
            List<TimeEntryDto> dtoList = getDtoList(timeEntryService.list());

            setResponseSuccess(dtoList);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(TimeEntryResourceService.class.getName()).log(Level.SEVERE, "System error TimeEntryResourceService list", ex);
        }

        return getResponseJson();
    }

    private List<TimeEntryDto> getDtoList(List<TimeEntry> entityList) throws Exception {
        List<TimeEntryDto> dtoList = new ArrayList<>();
        
        if (entityList.isEmpty()) {
            addWarningMessage("No time entries record found");
        } else {
            entityList.forEach(tc -> {
                
                TimeEntryDto dto = newTimeEntryDto();
                
                mappingService.updateTimeEntryDto(dto, tc);
                
                dtoList.add(dto);
                
            });
        }
        return dtoList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/find")
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String find(
            @QueryParam("employeeNumber") String employeeNumber,
            @QueryParam("yearMonthDate") String yearMonth,
            @QueryParam("clientNumber") String clientNumber) {
        
        try {
                   
            List<TimeEntryDto> dtoList = getDtoList(employeeNumber, yearMonth, clientNumber);

            setResponseSuccess(dtoList);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(TimeEntryResourceService.class.getName()).log(Level.SEVERE, "System error TimeEntryResourceService list", ex);
        }

        return getResponseJson();
    }
        
    private void validate(TimeEntryDto dto) throws Exception{
        // This is workaround for now
        // There should be dto validations
        // There should be bean validations too
        TimeEntry timeEntry = timeEntryService.newEntity();

        timeEntry.setTimeCode(new TimeCode());
        timeEntry.setEmployee(new Employee());

        mappingService.updateTimeEntry(timeEntry, dto);

        timeEntry.setGuid(dto.getUid());

        timeEntryService.validate(timeEntry);
    }

    private void setTimeCode(TimeEntry timeEntry, TimeCodeDto dto) throws Exception {

        if (dto != null && !AppUtils.isNullOrEmpty(dto.getUid())) {

            TimeCode timeCode = timeCodeService.findByGuid(dto.getUid());

            if (timeCode == null) {
                throw new DataValidationException("Invalid time code selected, does not exists.");
            }

            if (!timeCode.equals(timeEntry.getTimeCode())) {

                timeEntry.setTimeCode(timeCode);
            }

        } else if (timeEntry.getTimeCode() != null) {

            timeEntry.setTimeCode(null);
        }
    }

    private void setEmployee(TimeEntry timeEntry, EmployeeDto dto) throws Exception {

        if (dto != null && !AppUtils.isNullOrEmpty(dto.getNumber())) {

            Employee employee = employeeService.find(dto.getNumber());

            if (employee == null) {
                throw new DataValidationException("Invalid employee reference, does not exists.");
            }

            if (!employee.equals(timeEntry.getEmployee())) {

                timeEntry.setEmployee(employee);
            }

        } else if (timeEntry.getEmployee() != null) {

            timeEntry.setEmployee(null);
        }
    }

    private TimeEntryDto newTimeEntryDto() {
        TimeEntryDto dto = new TimeEntryDto();
        
        dto.setEmployee(new EmployeeDto());
        dto.setTimeCode(new TimeCodeDto());
        
        return dto;
    }

    public List<TimeEntryDto> getDtoList(String employeeNumber, String yearMonth, String clientNumber) throws Exception{
            Date yearMonthDate = AppUtils.dateFormat.parse(yearMonth);
                    
            List<TimeEntryDto> dtoList = getDtoList(timeEntryService.find(employeeNumber, yearMonthDate, clientNumber));
            
            return dtoList;
    }
    
}
