/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-14  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class InvoiceItemTaxDto {
    
    private String uid;

    /**
     * Get the value of uid
     *
     * @return the value of uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Set the value of uid
     *
     * @param uid new value of uid
     */
    public void setUid(String uid) {
        this.uid = uid;
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

    private SalesTaxDto salesTax;

    /**
     * Get the value of salesTax
     *
     * @return the value of salesTax
     */
    public SalesTaxDto getSalesTax() {
        return salesTax;
    }

    /**
     * Set the value of salesTax
     *
     * @param salesTax new value of salesTax
     */
    public void setSalesTax(SalesTaxDto salesTax) {
        this.salesTax = salesTax;
    }

}
