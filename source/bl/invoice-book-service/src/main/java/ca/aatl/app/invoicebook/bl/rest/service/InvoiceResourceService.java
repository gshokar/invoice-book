/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-23  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.ClientService;
import ca.aatl.app.invoicebook.bl.ejb.CompanyService;
import ca.aatl.app.invoicebook.bl.ejb.SalesInvoiceService;
import ca.aatl.app.invoicebook.bl.ejb.SalesItemService;
import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.bl.rest.ResourceResponseInitiated;
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoice;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItem;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.ClientDto;
import ca.aatl.app.invoicebook.dto.CompanyDto;
import ca.aatl.app.invoicebook.dto.ContactDto;
import ca.aatl.app.invoicebook.dto.InvoiceDto;
import ca.aatl.app.invoicebook.dto.InvoiceItemDto;
import ca.aatl.app.invoicebook.dto.InvoiceStatusDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.reports.ReportManager;
import ca.aatl.app.invoicebook.util.AppUtils;
import com.google.gson.JsonSyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
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
@Path("invoices")
public class InvoiceResourceService extends ResponseService {

    @EJB
    SalesInvoiceService salesInvoiceService;

    @EJB
    CompanyService companyService;

    @EJB
    ClientService clientService;

    @EJB
    SalesItemService salesItemService;

    @EJB
    MappingService mappingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/find")
    @Authenticated
    @ResourceResponseInitiated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String find(
            @QueryParam("number") String number,
            @QueryParam("date") String date,
            @QueryParam("clientNumber") String clientNumber) {

        try {

            Date invoiceDate = null;

            if (!AppUtils.isNullOrEmpty(date)) {

                invoiceDate = AppUtils.parseDate(date);

                if (invoiceDate == null) {
                    throw new DataValidationException("Invalid invoice date in search criteria");
                }
            }

            List<InvoiceDto> invoiceDtos = new ArrayList<>();

            List<SalesInvoice> salesInvoices = salesInvoiceService.find(number, invoiceDate, clientNumber);

            if (salesInvoices.isEmpty()) {
                addWarningMessage("No invoice record found");
            } else {

                invoiceDtos = mappingService.invoiceDtos(salesInvoices);

            }

            setResponseSuccess(invoiceDtos);

        } catch (DataValidationException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid invoice search data - " + ex.getMessage());

            Logger.getLogger(InvoiceResourceService.class.getName()).log(Level.INFO, "Invalid invoice search criteria Json", ex);
        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(InvoiceResourceService.class.getName()).log(Level.SEVERE, "System error InvoiceResourceService find", ex);
        }

        return getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{number}")
    @Authenticated
    public String getInvoice(@PathParam("number") String number) {
        try {

            InvoiceDto dto = getInvoiceDto(number);

            setResponseSuccess(dto);

        } catch (DataValidationException dex) {
            setResponseError(ErrorResponse.CODE_BAD_REQUEST, dex.getValidationMessage());
        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(InvoiceResourceService.class.getName()).log(Level.SEVERE, "System error InvoiceResourceService getClient", ex);
        }

        return getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/print")
    @Authenticated
    @ResourceResponseInitiated
    //@RolesAllowed(AppSecurity.ROLE_ADMIN)
    public String print(
            @QueryParam("number") String number) {

        try {

            InvoiceDto dto = getInvoiceDto(number);
            
            formatPhoneNumbersForDisplay(dto);
            
            ReportManager reportManager = new ReportManager();
            
            byte[] pdf = reportManager.salesInvoicePdf(this.getGson().toJson(dto));
            
            setResponseSuccess(Base64.getEncoder().encodeToString(pdf));

        } catch (DataValidationException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid invoice number for print request - " + ex.getMessage());

            Logger.getLogger(InvoiceResourceService.class.getName()).log(Level.INFO, "Invalid invoice number Json", ex);
        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(InvoiceResourceService.class.getName()).log(Level.SEVERE, "System error InvoiceResourceService find", ex);
        }

        return getResponseJson();
    }

    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    @ResourceResponseInitiated
    public String save(@Context SecurityContext sc, String json) {

        try {

            InvoiceDto dto = getDto(InvoiceDto.class, json);

            validate(dto);

            SalesInvoice invoice = null;

            if (AppUtils.isNullOrEmpty(dto.getNumber())) {
                invoice = salesInvoiceService.newEntity();
                invoice.setCompany(companyService.find("100001"));
            } else {
                invoice = salesInvoiceService.find(dto.getNumber());

                if (invoice == null) {
                    throw new DataValidationException("Invalid invoice number do not exists.");
                }
            }

            updateClient(invoice, dto.getClient());
            updateInvoiceDate(invoice, dto.getDate());
            updateItems(invoice, dto.getItems());

            invoice.setLastUpdatedBy(getUserId(sc));
            invoice.setLastUpdatedDate(Calendar.getInstance().getTime());

            salesInvoiceService.save(invoice);

            mappingService.updateSalesInvoiceDto(dto, invoice);

            setResponseSuccess(dto);

        } catch (JsonSyntaxException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, "Invalid invoice data - " + ex.getMessage());

            Logger.getLogger(InvoiceResourceService.class.getName()).log(Level.INFO, "Invalid InvoiceDto Json for update", ex);

        } catch (DataValidationException ex) {

            setResponseError(ErrorResponse.CODE_BAD_REQUEST, ex.getValidationMessage());

            Logger.getLogger(InvoiceResourceService.class.getName()).log(Level.INFO, "Invalid InvoiceDto data for update", ex);

        } catch (Exception ex) {

            setResponseError("System error - " + ex.getMessage());

            Logger.getLogger(InvoiceResourceService.class.getName()).log(Level.SEVERE, "System error InvoiceResourceService update", ex);
        }

        return this.getResponseJson();
    }

    private void validate(InvoiceDto dto) throws Exception {
        boolean isValid = true;

        StringBuilder sb = new StringBuilder();
        Date invoiceDate = AppUtils.parseDate(dto.getDate());

        if (invoiceDate == null) {
            isValid = false;
            sb.append(System.lineSeparator());
            sb.append("Enter the invoice date.");
        }

        if (dto.getClient() == null || AppUtils.isNullOrEmpty(dto.getClient().getNumber())) {
            isValid = false;
            sb.append(System.lineSeparator());
            sb.append("Select the client.");
        }

        if (AppUtils.isNullOrEmpty(dto.getNumber()) && dto.getItems().size() == 0) {
            isValid = false;
            sb.append(System.lineSeparator());
            sb.append("Add the item to invoice before save.");
        }

        for (InvoiceItemDto it : dto.getItems()) {
            if (it.getSalesItem() == null || AppUtils.isNullOrEmpty(it.getSalesItem().getCode())) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Select the invoice item.");
            }

            if (AppUtils.isNullOrEmpty(it.getDescription())) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the item description.");
            }

            if (it.getQuantity() <= 0) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the valid item quantity.");
            }

            if (it.getRate() <= 0) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the valid item rate.");
            }
        }

        if (!isValid) {
            throw new DataValidationException(sb.toString());
        }
    }

    private void updateClient(SalesInvoice invoice, ClientDto dto) throws Exception {

        if (dto != null && !AppUtils.isNullOrEmpty(dto.getNumber())) {

            if (invoice.getClient() == null || !invoice.getClient().getNumber().equals(dto.getNumber())) {
                Client client = clientService.find(dto.getNumber());

                if (client == null) {
                    throw new DataValidationException("Invalid client selected, does not exists.");
                }

                invoice.setClient(client);
            }
        }
    }

    private void updateInvoiceDate(SalesInvoice invoice, String dateValue) {

        if (!AppUtils.isNullOrEmpty(dateValue)) {
            try {
                invoice.setDate(AppUtils.dateFormat.parse(dateValue));
            } catch (ParseException ex) {

            }
        }
    }

    private void updateItems(SalesInvoice invoice, List<InvoiceItemDto> dtoInvoiceItems) throws Exception {

        if (dtoInvoiceItems != null && !dtoInvoiceItems.isEmpty()) {

            if (invoice.getItems() == null) {
                invoice.setItems(new ArrayList<>());
            }

            for (InvoiceItemDto dtoItem : dtoInvoiceItems) {
                updateInvoiceItem(invoice.getItems(), dtoItem);

                invoice.getItems().forEach(i -> {
                    if (i.getInvoice() == null) {
                        i.setInvoice(invoice);
                    }
                });
            }
        }
    }

    private void updateInvoiceItem(List<SalesInvoiceItem> items, InvoiceItemDto dtoItem) throws Exception {
        if (dtoItem != null) {

            SalesInvoiceItem invoiceItem = null;

            if (AppUtils.isNullOrEmpty(dtoItem.getUid())) {

                if (dtoItem.getSalesItem() != null && !AppUtils.isNullOrEmpty(dtoItem.getSalesItem().getCode())) {

                    SalesItem salesItem = salesItemService.find(dtoItem.getSalesItem().getCode());

                    if (salesItem == null) {
                        throw new DataValidationException("Invalid salse item selected, does not exists.");
                    }

                    invoiceItem = new SalesInvoiceItem();

                    invoiceItem.setSalesItem(salesItem);

                    items.add(invoiceItem);
                }
            } else {
                invoiceItem = items.stream().filter(i -> i.getGuid().equals(dtoItem.getUid())).findFirst().orElse(null);
            }

            if (invoiceItem != null) {
                mappingService.updateInvoiceItem(invoiceItem, dtoItem);
            }
        }
    }

    public InvoiceDto getInvoiceDto(String number) throws Exception {

        InvoiceDto dto = newInvoiceDto();

        if (number != null && !number.trim().isEmpty()) {

            SalesInvoice salesInvoice = salesInvoiceService.find(number);

            if (salesInvoice != null) {

                mappingService.updateSalesInvoiceDto(dto, salesInvoice);

            } else {
                throw new DataValidationException("Invoice number " + number + " do not exists.");
            }

        } else {
            throw new DataValidationException("Invalid invoiceS number");
        }

        return dto;
    }

    private InvoiceDto newInvoiceDto() {
        InvoiceDto dto = new InvoiceDto();

        dto.setClient(new ClientDto());
        dto.setCompany(new CompanyDto());
        dto.setItems(new ArrayList<>());
        dto.setStatus(new InvoiceStatusDto());
        dto.setTaxes(new ArrayList<>());

        return dto;
    }

    private void formatPhoneNumbersForDisplay(InvoiceDto dto) {
        if(dto != null){
            if(dto.getClient() != null){
                formatPhoneNumber(dto.getClient().getContact());
            }
            
            if(dto.getCompany() != null){
                formatPhoneNumber(dto.getCompany().getContact());
            }
        }
    }

    private void formatPhoneNumber(ContactDto contactDto) {
        if(contactDto != null){
            contactDto.setPhone(AppUtils.formatPhoneNumber(contactDto.getPhone()));
        }
    }
}
