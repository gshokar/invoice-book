/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-02  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.dto.TimeSheetDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.reports.ReportManager;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author GShokar
 */

@Stateless
@LocalBean
@Path("timesheet")
public class TimeSheetResourceService extends ResponseService{
    
    @EJB
    EmployeeResourceService employeeResource;
    
    @EJB
    CompanyResourceService companyResource;
    
    @EJB
    ClientResourceService clientResource;
    
    @EJB
    TimeEntryResourceService timeEntryResource;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/print")
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String print(
            @QueryParam("employeeNumber") String employeeNumber,
            @QueryParam("yearMonthDate") String yearMonth,
            @QueryParam("clientNumber") String clientNumber) {
        
        try {
                    
            TimeSheetDto timeSheetDto = newTimeSheetDto();
            
            timeSheetDto.setMonth(yearMonth);
            timeSheetDto.setCompany(companyResource.companyDto());
            timeSheetDto.setEmployee(employeeResource.getDto(employeeNumber));
            timeSheetDto.setClient(clientResource.getDto(clientNumber));
            timeSheetDto.setTimeEntries(timeEntryResource.getDtoList(employeeNumber, yearMonth, clientNumber));
            
            ReportManager reportManager = new ReportManager();
            
            byte[] pdf = reportManager.timeSheetPdf(this.getGson().toJson(timeSheetDto));
            
            setResponseSuccess(Base64.getEncoder().encodeToString(pdf));

        } catch (DataValidationException dex) {
            
            setResponseError(ErrorResponse.CODE_BAD_REQUEST, dex.getValidationMessage());
            
        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(TimeEntryResourceService.class.getName()).log(Level.SEVERE, "System error TimeEntryResourceService printTimeSheet", ex);
        }

        return getResponseJson();
    }
    
    private TimeSheetDto newTimeSheetDto() {
        TimeSheetDto dto = new TimeSheetDto();

        return dto;
    }
}
