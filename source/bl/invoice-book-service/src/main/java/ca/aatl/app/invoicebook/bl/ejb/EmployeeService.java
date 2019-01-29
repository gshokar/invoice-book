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
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.EmployeeDao;
import ca.aatl.app.invoicebook.data.jpa.entity.Address;
import ca.aatl.app.invoicebook.data.jpa.entity.Contact;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee;
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
public class EmployeeService {

    @EJB
    EmployeeDao dao;

    @EJB
    LookupService lookupService;

    @EJB
    SequenceService sequenceService;

    public List<Employee> find(String name, String phone) throws Exception {
        return dao.find(name, phone);
    }

    public Employee find(String number) throws Exception {

        return dao.find(number);
    }

    public Employee newEntity() throws Exception {

        Employee employee = new Employee();

        employee.setActive(true);

        employee.setAddress(new Address());

        employee.getAddress().setCountry(lookupService.defaultCountry());
        employee.getAddress().setProvince(lookupService.defaultProvince());

        employee.setContact(new Contact());

        return employee;
    }

    public void save(Employee entity) throws Exception {

        beforeSave(entity);

        dao.save(entity);
    }

    private void beforeSave(Employee entity) throws DataValidationException {

        validate(entity);

        if (entity.getId() == null) {
            entity.setAddedBy(entity.getLastUpdatedBy());
            entity.setAddedDate(entity.getLastUpdatedDate());
            entity.getGuid();

            synchronized (this) {
                entity.setNumber(sequenceService.next("EmployeeNumber"));
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

    public void validate(Employee entity) throws DataValidationException {

        boolean rtnValue = true;
        boolean checkDuplicate = true;

        StringBuilder sb = new StringBuilder();

        if (entity != null) {

            if (AppUtils.isNullOrEmpty(entity.getFirstName())) {
                rtnValue = false;
                checkDuplicate = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the employee first name.");
            }

            if (AppUtils.isNullOrEmpty(entity.getLastName())) {
                rtnValue = false;
                checkDuplicate = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the employee last name.");
            }

            if (entity.getBirthDate() == null) {
                rtnValue = false;
                checkDuplicate = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the employee date of birth.");
            } else if (entity.getBirthDate().after(AppUtils.currentDate())) {
                rtnValue = false;
                checkDuplicate = false;
                sb.append(System.lineSeparator());
                sb.append("Employee's date of birth can't be in future.");
            }

            if (checkDuplicate && dao.isExists(entity.getId(), entity.getName(), entity.getBirthDate())) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("This employee already exists.");
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

    public List<Employee> list() throws Exception{
        return dao.list();
    }
}
