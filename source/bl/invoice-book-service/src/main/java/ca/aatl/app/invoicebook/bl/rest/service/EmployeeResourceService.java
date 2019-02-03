/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-25  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.EmployeeService;
import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.AddressDto;
import ca.aatl.app.invoicebook.dto.ContactDto;
import ca.aatl.app.invoicebook.dto.EmployeeDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
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
import javax.ws.rs.PathParam;
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
@Path("employees")
public class EmployeeResourceService extends ResponseService {

    @EJB
    EmployeeService employeeService;

    @EJB
    MappingService mappingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/find")
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String find(
            @QueryParam("name") String name,
            @QueryParam("phone") String phone) {

        try {

            List<EmployeeDto> dtoEmployees = new ArrayList<>();

            List<Employee> employees = employeeService.find(name, phone);

            if (employees.isEmpty()) {
                addWarningMessage("No employee record found");
            } else {
                employees.forEach(c -> {

                    EmployeeDto dto = newEmployeeDto();

                    mappingService.updateEmployeeDto(dto, c);

                    dtoEmployees.add(dto);

                });
            }

            setResponseSuccess(dtoEmployees);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.SEVERE, "System error ClientResponseService find", ex);
        }

        return getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{number}")
    @Authenticated
    public String getEmployee(@PathParam("number") String number) {
        try {

            EmployeeDto dto = getDto(number);

            setResponseSuccess(dto);

        } catch (DataValidationException dex) {
            setResponseError(ErrorResponse.CODE_BAD_REQUEST, dex.getValidationMessage());
        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(EmployeeResourceService.class.getName()).log(Level.SEVERE, "System error EmployeeResponseService getEmployee", ex);
        }

        return getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String list() {
        try {
            List<EmployeeDto> dtoList = new ArrayList<>();

            List<Employee> employees = employeeService.list();

            if (employees.isEmpty()) {
                addWarningMessage("No employee record found");
            } else {
                employees.forEach(c -> {

                    EmployeeDto dto = newEmployeeDto();

                    mappingService.updateEmployeeDto(dto, c);

                    dtoList.add(dto);

                });
            }

            setResponseSuccess(dtoList);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(EmployeeResourceService.class.getName()).log(Level.SEVERE, "System error EmployeeResourceService list", ex);
        }

        return getResponseJson();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    public String save(@Context SecurityContext sc, String json) {

        try {

            EmployeeDto dto = getDto(EmployeeDto.class, json);

            validate(dto);

            Employee employee = null;

            if (dto.getNumber() != null && dto.getNumber().trim().length() == 0) {

                employee = employeeService.newEntity();

            } else {
                employee = employeeService.find(dto.getNumber());
            }

            mappingService.updateEmployee(employee, dto);

            employee.setLastUpdatedBy(getUserId(sc));
            employee.setLastUpdatedDate(Calendar.getInstance().getTime());

            employeeService.save(employee);

            mappingService.updateEmployeeDto(dto, employee);

            setResponseSuccess(dto);

        } catch (JsonSyntaxException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid employee data - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid EmployeeDto Json for update", ex);

        } catch (DataValidationException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, ex.getValidationMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.INFO, "Invalid EmployeeDto data for update", ex);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(ClientResourceService.class.getName()).log(Level.SEVERE, "System error EmployeeResponseService update", ex);
        }

        return this.getResponseJson();
    }

    private EmployeeDto newEmployeeDto() {
        EmployeeDto dto = new EmployeeDto();

        dto.setAddress(new AddressDto());
        dto.setContact(new ContactDto());
        return dto;
    }

    private void validate(EmployeeDto dto) throws Exception {
        // This is workaround for now
        // There should be dto validations
        // There should be bean validations too
        Employee employee = employeeService.newEntity();

        mappingService.updateEmployee(employee, dto);

        //employee.setNumber(dto.getNumber());
        employeeService.validate(employee);
    }

    public EmployeeDto getDto(String number) throws Exception {
        EmployeeDto dto = newEmployeeDto();

        if (number != null && !number.trim().isEmpty()) {

            Employee employee = employeeService.find(number);

            if (employee != null) {

                mappingService.updateEmployeeDto(dto, employee);

            } else {
                throw new DataValidationException("Employee number " + number + " do not exists.");
            }

        } else {
            throw new DataValidationException("Invalid employee number");
        }

        return dto;
    }
}
