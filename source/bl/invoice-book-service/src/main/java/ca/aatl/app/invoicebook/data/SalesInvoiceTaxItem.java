/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Apr-05  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data;

import java.util.Objects;

/**
 *
 * @author GShokar
 */
public class SalesInvoiceTaxItem {
    
    private static final String FORMAT_CODE_AND_RATE = "%1$s (%2$.2f%%)";
    
    private String code;

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    private double rate;

    /**
     * Get the value of rate
     *
     * @return the value of rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Set the value of rate
     *
     * @param rate new value of rate
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    private double amount;

    /**
     * Get the value of amount
     *
     * @return the value of amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Set the value of amount
     *
     * @param amount new value of amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String codeAndRateText(){
        
        return String.format(FORMAT_CODE_AND_RATE, getCode(), getRate());
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalesInvoiceTaxItem other = (SalesInvoiceTaxItem) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

}
