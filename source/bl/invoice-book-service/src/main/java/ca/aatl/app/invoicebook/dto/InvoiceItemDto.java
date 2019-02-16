/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-03  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GShokar
 */
public class InvoiceItemDto {
    
    private String invoiceNumber;

    /**
     * Get the value of invoiceNumber
     *
     * @return the value of invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Set the value of invoiceNumber
     *
     * @param invoiceNumber new value of invoiceNumber
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    private int lineNumber;

    /**
     * Get the value of lineNumber
     *
     * @return the value of lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Set the value of lineNumber
     *
     * @param lineNumber new value of lineNumber
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

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

    private String description;

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private double quantity;

    /**
     * Get the value of quantity
     *
     * @return the value of quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Set the value of quantity
     *
     * @param quantity new value of quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
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

    private double taxAmount;

    /**
     * Get the value of taxAmount
     *
     * @return the value of taxAmount
     */
    public double getTaxAmount() {
        return taxAmount;
    }

    /**
     * Set the value of taxAmount
     *
     * @param taxAmount new value of taxAmount
     */
    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    private double totalAmount;

    /**
     * Get the value of totalAmount
     *
     * @return the value of totalAmount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Set the value of totalAmount
     *
     * @param totalAmount new value of totalAmount
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private List<InvoiceItemTaxDto> taxes = new ArrayList<>();

    /**
     * Get the value of taxes
     *
     * @return the value of taxes
     */
    public List<InvoiceItemTaxDto> getTaxes() {
        return taxes;
    }

    /**
     * Set the value of taxes
     *
     * @param taxes new value of taxes
     */
    public void setTaxes(List<InvoiceItemTaxDto> taxes) {
        this.taxes = taxes;
    }

    private SalesItemDto salesItem;

    /**
     * Get the value of salesItem
     *
     * @return the value of salesItem
     */
    public SalesItemDto getSalesItem() {
        return salesItem;
    }

    /**
     * Set the value of salesItem
     *
     * @param salesItem new value of salesItem
     */
    public void setSalesItem(SalesItemDto salesItem) {
        this.salesItem = salesItem;
    }

}
