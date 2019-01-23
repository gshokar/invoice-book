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
import ca.aatl.app.invoicebook.data.jpa.entity.Address;
import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientLocation;
import ca.aatl.app.invoicebook.data.jpa.entity.Contact;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee;
import ca.aatl.app.invoicebook.data.jpa.entity.Province;
import ca.aatl.app.invoicebook.dto.AddressDto;
import ca.aatl.app.invoicebook.dto.ClientDto;
import ca.aatl.app.invoicebook.dto.ClientLocationDto;
import ca.aatl.app.invoicebook.dto.ContactDto;
import ca.aatl.app.invoicebook.dto.EmployeeDto;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
import java.text.ParseException;
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

                updateAddressDto(dto.getAddress(), client.primaryAddress().getAddress());

            }

            if (client.hasContact()) {

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
}
