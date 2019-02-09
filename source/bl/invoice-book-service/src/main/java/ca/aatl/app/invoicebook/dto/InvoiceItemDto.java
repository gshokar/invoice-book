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

    private InvoiceItemTypeDto itemType;

    /**
     * Get the value of itemType
     *
     * @return the value of itemType
     */
    public InvoiceItemTypeDto getItemType() {
        return itemType;
    }

    /**
     * Set the value of itemType
     *
     * @param itemType new value of itemType
     */
    public void setItemType(InvoiceItemTypeDto itemType) {
        this.itemType = itemType;
    }

}
