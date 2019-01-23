/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-09  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.ClientLocationDao;
import ca.aatl.app.invoicebook.data.jpa.entity.Address;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientLocation;
import ca.aatl.app.invoicebook.data.jpa.entity.Contact;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
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
public class ClientLocationService {

    @EJB
    ClientLocationDao dao;
    
    @EJB
    LookupService lookupService;
    
    @EJB
    SequenceService sequenceService;
            
    public List<ClientLocation> list(String clientNumber) {
        
        return dao.list(clientNumber);
    }

    public ClientLocation newEntity()  throws Exception{
        
        ClientLocation location = new ClientLocation();

        location.setActive(true);

        location.setAddress(new Address());
        
        location.getAddress().setCountry(lookupService.defaultCountry());
        location.getAddress().setProvince(lookupService.defaultProvince());

        location.setContact(new Contact());

        return location;
    }

    public void validate(ClientLocation entity) throws DataValidationException {
        boolean rtnValue = true;

        StringBuilder sb = new StringBuilder();

        if (entity != null) {

            if (AppUtils.isNullOrEmpty(entity.getName())) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the location name.");
            } else if (dao.isExists(entity.getId(), entity.getNumber(), entity.getName(), entity.getClient().getId())) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("This location name already exists.");
            }

            if (entity.getAddress() != null) {

                Address address = entity.getAddress();

                if (AppUtils.isNullOrEmpty(address.getAddress1())) {
                    rtnValue = false;
                    sb.append(System.lineSeparator());
                    sb.append("Enter the address.");
                }

                if (AppUtils.isNullOrEmpty(address.getCity())) {
                    rtnValue = false;
                    sb.append(System.lineSeparator());
                    sb.append("Enter the city.");
                }

                if (AppUtils.isNullOrEmpty(address.getPostalCode())) {
                    rtnValue = false;
                    sb.append(System.lineSeparator());
                    sb.append("Enter the postal code.");
                }
            }

            if (entity.getContact() != null) {
                Contact contact = entity.getContact();

                if (AppUtils.isNullOrEmpty(contact.getPhone())) {
                    rtnValue = false;
                    sb.append(System.lineSeparator());
                    sb.append("Enter the phone number.");
                }

            }
        }

        if (!rtnValue) {
            throw new DataValidationException(sb.toString());
        }
    }

    public ClientLocation find(String number) throws Exception {
        return dao.find(number);
    }

    public void save(ClientLocation clientLocation) throws Exception{
        beforeSave(clientLocation);

        dao.save(clientLocation);
    }

    private void beforeSave(ClientLocation entity) throws DataValidationException{
        validate(entity);

        if (entity.getId() == null) {
            entity.setAddedBy(entity.getLastUpdatedBy());
            entity.setAddedDate(entity.getLastUpdatedDate());
            entity.getGuid();

            synchronized (this) {
                entity.setNumber(sequenceService.next("ClientLocationNumber"));
            }
        }

        if (entity.getAddress() != null && entity.getAddress().getId() == null) {

            entity.getAddress().setAddedBy(entity.getLastUpdatedBy());
            entity.getAddress().setAddedDate(entity.getLastUpdatedDate());
            entity.getAddress().setLastUpdatedBy(entity.getLastUpdatedBy());
            entity.getAddress().setLastUpdatedDate(entity.getLastUpdatedDate());
            entity.getAddress().getGuid();
        }

        if (entity.getContact() != null && entity.getContact().getId() == null) {
            entity.getContact().setAddedBy(entity.getLastUpdatedBy());
            entity.getContact().setAddedDate(entity.getLastUpdatedDate());
            entity.getContact().setLastUpdatedBy(entity.getLastUpdatedBy());
            entity.getContact().setLastUpdatedDate(entity.getLastUpdatedDate());
            entity.getContact().getGuid();
        }
    }
    
}
