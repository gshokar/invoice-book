/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-31  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class CompanyDto extends CommonDto{
    
    private String taxRegNumber;

    /**
     * Get the value of taxRegNumber
     *
     * @return the value of taxRegNumber
     */
    public String getTaxRegNumber() {
        return taxRegNumber;
    }

    /**
     * Set the value of taxRegNumber
     *
     * @param taxRegNumber new value of taxRegNumber
     */
    public void setTaxRegNumber(String taxRegNumber) {
        this.taxRegNumber = taxRegNumber;
    }

}
