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
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class AddressDto {
    
    private String address1;

    /**
     * Get the value of address1
     *
     * @return the value of address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Set the value of address1
     *
     * @param address1 new value of address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    private String address2;

    /**
     * Get the value of address2
     *
     * @return the value of address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Set the value of address2
     *
     * @param address2 new value of address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    private String city;

    /**
     * Get the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the value of city
     *
     * @param city new value of city
     */
    public void setCity(String city) {
        this.city = city;
    }

    private String province;

    /**
     * Get the value of province
     *
     * @return the value of province
     */
    public String getProvince() {
        return province;
    }

    /**
     * Set the value of province
     *
     * @param province new value of province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    private String postalCode;

    /**
     * Get the value of postalCode
     *
     * @return the value of postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set the value of postalCode
     *
     * @param postalCode new value of postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
