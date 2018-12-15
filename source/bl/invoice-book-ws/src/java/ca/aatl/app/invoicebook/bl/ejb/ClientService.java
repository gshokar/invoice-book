/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-09  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.ClientDao;
import ca.aatl.app.invoicebook.data.jpa.entity.Address;
import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientAddress;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientContact;
import ca.aatl.app.invoicebook.data.jpa.entity.Contact;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
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
public class ClientService {

    @EJB
    ClientDao clientDao;

    @EJB
    LookupService lookupService;

    @EJB
    SequenceService sequenceService;
    
    public Client find(String number) throws Exception {

        return clientDao.find(number);
    }

    public List<Client> find(String name, String phone) throws Exception {
        return clientDao.find(name, phone);
    }

    public Client newClient() throws Exception {

        Client client = new Client();

        client.setActive(true);
        
        client.setAddresses(new ArrayList<>());

        ClientAddress ca = new ClientAddress();

        ca.setAddress(new Address());
        ca.setAddressType(lookupService.defaultAddressType());
        ca.setFromDate(AppUtils.currentDate());
        ca.setPrimary(true);

        ca.getAddress().setCountry(lookupService.defaultCountry());
        ca.getAddress().setProvince(lookupService.defaultProvince());

        client.getAddresses().add(ca);

        client.setContacts(new ArrayList<>());

        ClientContact cc = new ClientContact();

        cc.setContact(new Contact());
        cc.setContactType(lookupService.defaultContactType());
        cc.setFromDate(ca.getFromDate());
        cc.setPrimary(true);

        client.getContacts().add(cc);

        return client;
    }

    public void save(Client client) throws Exception {

        beforeSave(client);

        clientDao.save(client);
    }

    private void beforeSave(Client entity) throws DataValidationException {

        validate(entity);

        if (entity.getId() == null) {
            entity.setAddedBy(entity.getLastUpdatedBy());
            entity.setAddedDate(entity.getLastUpdatedDate());
            entity.getGuid();

            entity.setNumber(sequenceService.next("ClientNumber"));
        }

        if (entity.getAddresses() != null && !entity.getAddresses().isEmpty()) {

            entity.getAddresses().forEach(ca -> {

                if (ca.getId() == null) {
                    ca.setClient(entity);
                    ca.setAddedBy(entity.getLastUpdatedBy());
                    ca.setAddedDate(entity.getLastUpdatedDate());
                    ca.setLastUpdatedBy(entity.getLastUpdatedBy());
                    ca.setLastUpdatedDate(entity.getLastUpdatedDate());
                    ca.getGuid();
                }

                if (ca.getAddress().getId() == null) {
                    ca.getAddress().setAddedBy(entity.getLastUpdatedBy());
                    ca.getAddress().setAddedDate(entity.getLastUpdatedDate());
                    ca.getAddress().setLastUpdatedBy(entity.getLastUpdatedBy());
                    ca.getAddress().setLastUpdatedDate(entity.getLastUpdatedDate());
                    ca.getAddress().getGuid();
                }
            });
        }

        if (entity.getContacts() != null && !entity.getContacts().isEmpty()) {

            entity.getContacts().forEach(cc -> {

                if (cc.getId() == null) {
                    cc.setClient(entity);
                    cc.setAddedBy(entity.getLastUpdatedBy());
                    cc.setAddedDate(entity.getLastUpdatedDate());
                    cc.setLastUpdatedBy(entity.getLastUpdatedBy());
                    cc.setLastUpdatedDate(entity.getLastUpdatedDate());
                    cc.getGuid();
                }

                if (cc.getContact().getId() == null) {
                    cc.getContact().setAddedBy(entity.getLastUpdatedBy());
                    cc.getContact().setAddedDate(entity.getLastUpdatedDate());
                    cc.getContact().setLastUpdatedBy(entity.getLastUpdatedBy());
                    cc.getContact().setLastUpdatedDate(entity.getLastUpdatedDate());
                    cc.getContact().getGuid();
                }
            });
        }
    }

    public void validate(Client entity) throws DataValidationException {

        boolean rtnValue = true;

        StringBuilder sb = new StringBuilder();

        if (entity != null) {

            if (AppUtils.isNullOrEmpty(entity.getName())) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the client name.");
            }else if(clientDao.isExists(entity.getId(), entity.getNumber(), entity.getName())){
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("This client name already exists.");
            }
            
            
            if (entity.hasAddress()) {
                
                Address address = entity.primaryAddress().getAddress();
                
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

            if (entity.hasContact()) {
                Contact contact = entity.primaryContact().getContact();
                
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
}
