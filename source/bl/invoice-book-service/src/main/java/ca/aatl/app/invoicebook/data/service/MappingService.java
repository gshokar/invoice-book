/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-13  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.service;

import ca.aatl.app.invoicebook.bl.ejb.LookupService;
import ca.aatl.app.invoicebook.data.SalesInvoiceTaxItem;
import ca.aatl.app.invoicebook.data.jpa.entity.Address;
import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientLocation;
import ca.aatl.app.invoicebook.data.jpa.entity.Company;
import ca.aatl.app.invoicebook.data.jpa.entity.Contact;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee;
import ca.aatl.app.invoicebook.data.jpa.entity.Province;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoice;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItemTax;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceStatus;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesTaxRate;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesTax;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeCode;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeEntry;
import ca.aatl.app.invoicebook.dto.AddressDto;
import ca.aatl.app.invoicebook.dto.ClientDto;
import ca.aatl.app.invoicebook.dto.ClientLocationDto;
import ca.aatl.app.invoicebook.dto.CompanyDto;
import ca.aatl.app.invoicebook.dto.ContactDto;
import ca.aatl.app.invoicebook.dto.EmployeeDto;
import ca.aatl.app.invoicebook.dto.InvoiceDto;
import ca.aatl.app.invoicebook.dto.InvoiceItemDto;
import ca.aatl.app.invoicebook.dto.InvoiceItemTaxDto;
import ca.aatl.app.invoicebook.dto.InvoiceStatusDto;
import ca.aatl.app.invoicebook.dto.ItemUnitDto;
import ca.aatl.app.invoicebook.dto.SalesInvoiceTaxItemDto;
import ca.aatl.app.invoicebook.dto.SalesItemDto;
import ca.aatl.app.invoicebook.dto.SalesItemTaxRateDto;
import ca.aatl.app.invoicebook.dto.SalesItemTypeDto;
import ca.aatl.app.invoicebook.dto.SalesTaxDto;
import ca.aatl.app.invoicebook.dto.TimeCodeDto;
import ca.aatl.app.invoicebook.dto.TimeEntryDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
public class MappingService {

    @EJB
    LookupService lookupService;

    public void updateClientDto(ClientDto dto, Client client) {

        if (client != null && dto != null) {

            dto.setName(client.getName());
            dto.setNumber(client.getNumber());

            if (client.hasAddress()) {

                if (dto.getAddress() == null) {
                    dto.setAddress(new AddressDto());
                }

                updateAddressDto(dto.getAddress(), client.primaryAddress().getAddress());

            }

            if (client.hasContact()) {

                if (dto.getContact() == null) {
                    dto.setContact(new ContactDto());
                }

                updateContactDto(dto.getContact(), client.primaryContact().getContact());
            }
        }
    }

    public void updateAddressDto(AddressDto dto, Address address) {

        if (address != null && dto != null) {

            dto.setAddress1(address.getAddress1());
            dto.setAddress2(address.getAddress2());
            dto.setCity(address.getCity());
            dto.setPostalCode(address.getPostalCode());

            if (address.getProvince() != null) {
                dto.setProvince(address.getProvince().getCode());
            }
        }
    }

    public void updateAddress(Address address, AddressDto dto) throws DataValidationException, Exception {

        if (dto != null && address != null) {

            address.setAddress1(dto.getAddress1());
            address.setAddress2(dto.getAddress2());
            address.setCity(dto.getCity());
            address.setPostalCode(dto.getPostalCode());

            if (address.getProvince() != null) {

                Province province = lookupService.findProvince(dto.getProvince());

                if (province != null) {
                    address.setProvince(province);
                } else {

                    throw new DataValidationException("Invalid province code for address data.");
                }
            }
        }

    }

    public void updateContactDto(ContactDto dto, Contact contact) {

        if (contact != null && dto != null) {

            dto.setEmail(contact.getEmail());
            dto.setPhone(contact.getPhone());
        }
    }

    public void updateContact(Contact contact, ContactDto dto) {

        if (dto != null && contact != null) {

            contact.setEmail(dto.getEmail());
            contact.setPhone(dto.getPhone());
        }
    }

    public void updateClient(Client client, ClientDto dto) throws Exception {

        if (dto != null && client != null) {

            if (client.hasAddress()) {

                updateAddress(client.primaryAddress().getAddress(), dto.getAddress());
            }

            if (client.hasContact()) {
                updateContact(client.primaryContact().getContact(), dto.getContact());
            }

            client.setName(dto.getName());
        }
    }

    public void updateEmployeeDto(EmployeeDto dto, Employee employee) {

        if (employee != null && dto != null) {

            dto.setFirstName(employee.getFirstName());
            dto.setLastName(employee.getLastName());
            dto.setNumber(employee.getNumber());
            dto.setName(employee.getName());

            if (employee.getBirthDate() != null) {
                dto.setBirthDate(AppUtils.dateToString(employee.getBirthDate()));
            }

            if (employee.getAddress() != null) {

                updateAddressDto(dto.getAddress(), employee.getAddress());

            }

            if (employee.getContact() != null) {

                updateContactDto(dto.getContact(), employee.getContact());
            }
        }
    }

    public void updateEmployee(Employee employee, EmployeeDto dto) throws Exception {

        if (dto != null && employee != null) {

            if (employee.getAddress() != null) {

                updateAddress(employee.getAddress(), dto.getAddress());
            }

            if (employee.getContact() != null) {
                updateContact(employee.getContact(), dto.getContact());
            }

            employee.setFirstName(dto.getFirstName());
            employee.setLastName(dto.getLastName());

            if (!AppUtils.isNullOrEmpty(dto.getBirthDate())) {
                try {
                    employee.setBirthDate(AppUtils.dateFormat.parse(dto.getBirthDate()));
                } catch (ParseException ex) {

                }
            }
        }
    }

    public void updateClientLocationDto(ClientLocationDto dto, ClientLocation location) {
        if (location != null && dto != null) {

            dto.setName(location.getName());
            dto.setNumber(location.getNumber());

            if (location.getClient() != null) {
                dto.setClientNumber(location.getClient().getNumber());
            }

            if (location.getAddress() != null) {

                updateAddressDto(dto.getAddress(), location.getAddress());

            }

            if (location.getContact() != null) {

                updateContactDto(dto.getContact(), location.getContact());
            }
        }
    }

    public void updateClientLocation(ClientLocation clientLocation, ClientLocationDto dto) throws Exception {
        if (dto != null && clientLocation != null) {

            if (clientLocation.getAddress() != null) {

                updateAddress(clientLocation.getAddress(), dto.getAddress());
            }

            if (clientLocation.getContact() != null) {
                updateContact(clientLocation.getContact(), dto.getContact());
            }

            clientLocation.setName(dto.getName());

        }
    }

    public void updateTimeCode(TimeCode timeCode, TimeCodeDto dto) throws Exception {
        if (dto != null && timeCode != null) {

            timeCode.setName(dto.getName());
            timeCode.setActive(dto.isActive());
            timeCode.setChargeable(dto.isChargeable());

        }
    }

    public void updateTimeCodeDto(TimeCodeDto dto, TimeCode timeCode) {
        if (dto != null && timeCode != null) {

            dto.setActive(timeCode.isActive());
            dto.setChargeable(timeCode.isChargeable());
            dto.setName(timeCode.getName());
            dto.setUid(timeCode.getGuid());
            dto.setClient(new ClientDto());
            dto.setClientLocation(new ClientLocationDto());
            dto.setServiceItem(new SalesItemDto("", ""));

            if (timeCode.getClient() != null) {

                dto.getClient().setName(timeCode.getClient().getName());
                dto.getClient().setNumber(timeCode.getClient().getNumber());
            } else {
                dto.getClient().setNumber("");
                dto.getClient().setName("");
            }

            if (timeCode.getClientLocation() != null) {

                dto.getClientLocation().setName(timeCode.getClientLocation().getName());
                dto.getClientLocation().setNumber(timeCode.getClientLocation().getNumber());
            } else {
                dto.getClientLocation().setNumber("");
                dto.getClientLocation().setName("");
            }

            if (timeCode.getServiceItem() != null) {

                dto.getServiceItem().setName(timeCode.getServiceItem().getName());
                dto.getServiceItem().setCode(timeCode.getServiceItem().getGuid());
            }
        }
    }

    public void updateTimeEntry(TimeEntry entity, TimeEntryDto dto) {
        if (dto != null && entity != null) {

            entity.setApproved(dto.isApproved());
            entity.setCharged(dto.isCharged());

            if (!AppUtils.isNullOrEmpty(dto.getDate())) {
                try {
                    entity.setDate(AppUtils.dateFormat.parse(dto.getDate()));
                } catch (ParseException ex) {

                }
            }

            if (!AppUtils.isNullOrEmpty(dto.getEndTime())) {
                try {
                    entity.setEndTime(AppUtils.timeFormat.parse(dto.getEndTime()));
                } catch (ParseException ex) {

                }
            }

            if (!AppUtils.isNullOrEmpty(dto.getStartTime())) {
                try {
                    entity.setStartTime(AppUtils.timeFormat.parse(dto.getStartTime()));
                } catch (ParseException ex) {

                }
            }
        }
    }

    public void updateTimeEntryDto(TimeEntryDto dto, TimeEntry entity) {
        if (dto != null && entity != null) {

            dto.setDate(AppUtils.dateToString(entity.getDate()));
            dto.setEmployee(new EmployeeDto());
            dto.setEndTime(AppUtils.timeToString(entity.getEndTime()));
            dto.setStartTime(AppUtils.timeToString(entity.getStartTime()));
            dto.setHours(entity.getHours() == null ? 0 : entity.getHours().doubleValue());
            dto.setTimeCode(new TimeCodeDto());
            dto.setUid(entity.getGuid());
            dto.setApproved(entity.isApproved());
            dto.setCharged(entity.isCharged());

            if (entity.getEmployee() != null) {
                dto.getEmployee().setNumber(entity.getEmployee().getNumber());
                dto.getEmployee().setName(entity.getEmployee().getName());
            }

            if (entity.getTimeCode() != null) {
                dto.getTimeCode().setUid(entity.getTimeCode().getGuid());
                dto.getTimeCode().setName(entity.getTimeCode().getName());
            }
        }
    }

    public void updateCompanyDto(CompanyDto dto, Company entity) {
        if (entity != null && dto != null) {

            dto.setName(entity.getName());
            dto.setNumber(entity.getNumber());
            dto.setTaxRegNumber(entity.getTaxRegNumber() == null ? "" : entity.getTaxRegNumber());

            if (entity.hasAddress()) {
                
                if (dto.getAddress() == null) {
                    dto.setAddress(new AddressDto());
                }
                
                updateAddressDto(dto.getAddress(), entity.primaryAddress().getAddress());

            }

            if (entity.hasContact()) {

                if (dto.getContact() == null) {
                    dto.setContact(new ContactDto());
                }

                updateContactDto(dto.getContact(), entity.primaryContact().getContact());
            }
        }
    }

    public void updateSalesItemDto(SalesItemDto dto, SalesItem entity) {
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setRate(entity.getRate().doubleValue());

        if (entity.getItemType() != null) {
            dto.setItemType(new SalesItemTypeDto(entity.getItemType().getGuid(), entity.getItemType().getName()));
        }

        if (entity.getUnit() != null) {
            dto.setUnit(new ItemUnitDto(entity.getUnit().getGuid(), entity.getUnit().getName()));
        }
    }

    public void updateInvoiceItem(SalesInvoiceItem entity, InvoiceItemDto dto) {
        entity.setDescription(dto.getDescription());
        entity.setLineNumber(dto.getLineNumber());
        entity.setQuantity(BigDecimal.valueOf(dto.getQuantity()));
        entity.setRate(BigDecimal.valueOf(dto.getRate()));
    }

    public void updateSalesInvoiceDto(InvoiceDto dto, SalesInvoice invoice) {
        dto.setAmount(invoice.getAmount().doubleValue());
        dto.setDate(AppUtils.dateToString(invoice.getDate()));
        dto.setNumber(invoice.getNumber());
        dto.setPaidAmount(invoice.getPaidAmount().doubleValue());
        dto.setTaxAmount(invoice.getTaxAmount().doubleValue());
        dto.setTotalAmount(invoice.getTotalAmount().doubleValue());

        if (dto.getClient() != null) {
            updateClientDto(dto.getClient(), invoice.getClient());
        }

        if (dto.getCompany() != null) {
            updateCompanyDto(dto.getCompany(), invoice.getCompany());
        }

        if (dto.getStatus() != null) {
            updateSalesInvoiceStatusDto(dto.getStatus(), invoice.getStatus());
        }

        if (dto.getItems() == null) {
            dto.setItems(new ArrayList<>());
        }

        dto.getItems().clear();

        if (invoice.getItems() != null && !invoice.getItems().isEmpty()) {
            InvoiceItemDto itemDto = null;

            for (SalesInvoiceItem item : invoice.getItems()) {
                itemDto = new InvoiceItemDto();

                updateSalesInvoiceItemDto(itemDto, item);

                dto.getItems().add(itemDto);
            }
        }

        if (dto.getTaxes() == null) {
            dto.setTaxes(new ArrayList<>());
        }

        dto.getTaxes().clear();

        List<SalesInvoiceTaxItem> taxItems = invoice.taxItems();

        if (taxItems != null && !taxItems.isEmpty()) {

            for (SalesInvoiceTaxItem taxItem : taxItems) {
                SalesInvoiceTaxItemDto taxItemDto = new SalesInvoiceTaxItemDto();

                updateSalesInvoiceTaxItemDto(taxItemDto, taxItem);
                
                dto.getTaxes().add(taxItemDto);
            }
        }
    }

    public void updateSalesInvoiceStatusDto(InvoiceStatusDto statusDto, SalesInvoiceStatus status) {
        statusDto.setCode(status.getName());
        statusDto.setName(status.getName());
    }

    public void updateSalesInvoiceItemDto(InvoiceItemDto itemDto, SalesInvoiceItem item) {
        itemDto.setAmount(item.getAmount().doubleValue());
        itemDto.setDescription(item.getDescription());
        itemDto.setLineNumber(item.getLineNumber());
        itemDto.setQuantity(item.getQuantity().doubleValue());
        itemDto.setRate(item.getRate().doubleValue());
        itemDto.setTaxAmount(item.getTaxAmount().doubleValue());
        itemDto.setTotalAmount(item.getTotalAmount().doubleValue());
        itemDto.setUid(item.getGuid());

        if (item.getInvoice() != null) {
            itemDto.setInvoiceNumber(item.getInvoice().getNumber());
        }

        if (itemDto.getSalesItem() == null) {
            itemDto.setSalesItem(new SalesItemDto());
        }

        updateSalesItemDto(itemDto.getSalesItem(), item.getSalesItem());

        if (itemDto.getTaxes() == null) {
            itemDto.setTaxes(new ArrayList<>());
        }

        itemDto.getTaxes().clear();

        for (SalesInvoiceItemTax itemTax : item.getTaxes()) {
            InvoiceItemTaxDto itemTaxDto = new InvoiceItemTaxDto();

            updateInvoiceItemTaxDto(itemTaxDto, itemTax);

            itemDto.getTaxes().add(itemTaxDto);
        }
    }

    public void updateInvoiceItemTaxDto(InvoiceItemTaxDto dto, SalesInvoiceItemTax entity) {
        dto.setAmount(entity.getAmount().doubleValue());
        dto.setUid(entity.getGuid());

        if (dto.getSalesItemTaxRate() == null) {
            dto.setSalesItemTaxRate(new SalesItemTaxRateDto());
        }

        updateSalesItemTaxRateDto(dto.getSalesItemTaxRate(), entity.getTaxRate());
    }

    public void updateSalesItemTaxRateDto(SalesItemTaxRateDto dto, SalesTaxRate entity) {

//        if(dto.getCountry() == null){
//            dto.setCountry(new CountryDto());
//        }
//        
//        updateCountryDto(dto.getCountry())
        dto.setRate(entity.getRate().doubleValue());

        if (dto.getTax() == null) {
            dto.setTax(new SalesTaxDto());
        }

        updateSalesTaxDto(dto.getTax(), entity.getTax());
    }

    public void updateSalesTaxDto(SalesTaxDto dto, SalesTax entity) {
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
    }

    public void updateSalesInvoiceTaxItemDto(SalesInvoiceTaxItemDto dto, SalesInvoiceTaxItem taxItem) {
        dto.setAmount(taxItem.getAmount());
        dto.setCode(taxItem.getCode());
        dto.setCodeRateText(taxItem.codeAndRateText());
        dto.setName(taxItem.getName());
        dto.setRate(taxItem.getRate());
    }

    public List<InvoiceDto> invoiceDtos(List<SalesInvoice> salesInvoices) {
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        for (SalesInvoice salesInvoice : salesInvoices) {
            InvoiceDto invoiceDto = new InvoiceDto();

            invoiceDto.setAmount(salesInvoice.getAmount().doubleValue());
            invoiceDto.setDate(AppUtils.dateToString(salesInvoice.getDate()));
            invoiceDto.setNumber(salesInvoice.getNumber());
            invoiceDto.setTaxAmount(salesInvoice.getTaxAmount().doubleValue());
            invoiceDto.setTotalAmount(salesInvoice.getTotalAmount().doubleValue());

            if (salesInvoice.getClient() != null) {
                ClientDto clientDto = new ClientDto();

                clientDto.setName(salesInvoice.getClient().getName());
                clientDto.setNumber(salesInvoice.getClient().getNumber());

                invoiceDto.setClient(clientDto);
            }

            invoiceDtos.add(invoiceDto);
        }
        return invoiceDtos;
    }
}
