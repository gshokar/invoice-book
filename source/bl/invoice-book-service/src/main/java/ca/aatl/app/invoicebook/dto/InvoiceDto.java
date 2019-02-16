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
public class InvoiceDto {

    private CompanyDto company;

    /**
     * Get the value of company
     *
     * @return the value of company
     */
    public CompanyDto getCompany() {
        return company;
    }

    /**
     * Set the value of company
     *
     * @param company new value of company
     */
    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    private ClientDto client;

    /**
     * Get the value of client
     *
     * @return the value of client
     */
    public ClientDto getClient() {
        return client;
    }

    /**
     * Set the value of client
     *
     * @param client new value of client
     */
    public void setClient(ClientDto client) {
        this.client = client;
    }
    private String date;

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(String date) {
        this.date = date;
    }

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

    private List<InvoiceItemDto> items = new ArrayList<>();

    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public List<InvoiceItemDto> getItems() {
        return items;
    }

    /**
     * Set the value of items
     *
     * @param items new value of items
     */
    public void setItems(List<InvoiceItemDto> items) {
        this.items = items;
    }

    private InvoiceStatusDto status;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public InvoiceStatusDto getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(InvoiceStatusDto status) {
        this.status = status;
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

    private double paidAmount;

    /**
     * Get the value of paidAmount
     *
     * @return the value of paidAmount
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * Set the value of paidAmount
     *
     * @param paidAmount new value of paidAmount
     */
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

}
