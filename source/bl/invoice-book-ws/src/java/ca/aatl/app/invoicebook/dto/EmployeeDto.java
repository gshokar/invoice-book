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
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class EmployeeDto {
    
    private String number;

    /**
     * Get the value of number
     *
     * @return the value of number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Set the value of number
     *
     * @param number new value of number
     */
    public void setNumber(String number) {
        this.number = number;
    }

        private String firstName;

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String lastName;

    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String birthDate;

    /**
     * Get the value of birthDate
     *
     * @return the value of birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Set the value of birthDate
     *
     * @param birthDate new value of birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    private AddressDto address;

    /**
     * Get the value of address
     *
     * @return the value of address
     */
    public AddressDto getAddress() {
        return address;
    }

    /**
     * Set the value of address
     *
     * @param address new value of address
     */
    public void setAddress(AddressDto address) {
        this.address = address;
    }
    
    private ContactDto contact;

    /**
     * Get the value of contact
     *
     * @return the value of contact
     */
    public ContactDto getContact() {
        return contact;
    }

    /**
     * Set the value of contact
     *
     * @param contact new value of contact
     */
    public void setContact(ContactDto contact) {
        this.contact = contact;
    }
}
