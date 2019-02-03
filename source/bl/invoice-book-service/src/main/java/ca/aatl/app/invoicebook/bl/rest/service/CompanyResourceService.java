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

import ca.aatl.app.invoicebook.bl.ejb.CompanyService;
import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.Company;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.AddressDto;
import ca.aatl.app.invoicebook.dto.ClientDto;
import ca.aatl.app.invoicebook.dto.CompanyDto;
import ca.aatl.app.invoicebook.dto.ContactDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
@Path("company")
public class CompanyResourceService extends ResponseService {

    @EJB
    CompanyService companyService;

    @EJB
    MappingService mappingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public String getCompany() {
        try {

            CompanyDto dto = companyDto();

            setResponseSuccess(dto);

        } catch (DataValidationException dex) {
            setResponseError(ErrorResponse.CODE_BAD_REQUEST, dex.getValidationMessage());
        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(CompanyResourceService.class.getName()).log(Level.SEVERE, "System error ClientResponseService getClient", ex);
        }

        return getResponseJson();
    }

    public CompanyDto companyDto() throws Exception{
        return getDto("100001");
    }
    
    public CompanyDto getDto(String number) throws Exception {

        CompanyDto dto = newCompanyDto();

        if (number != null && !number.trim().isEmpty()) {

            Company entity = companyService.find(number);

            if (entity != null) {

                mappingService.updateCompanyDto(dto, entity);
            } else {
                throw new DataValidationException("Company number do not exists, please provide valid company number");
            }
        } else {
            throw new DataValidationException("Invalid company number, please provide valid company number");
        }

        return dto;
    }

    private CompanyDto newCompanyDto() {
        CompanyDto dto = new CompanyDto();

        dto.setAddress(new AddressDto());
        dto.setContact(new ContactDto());
        return dto;
    }
}
