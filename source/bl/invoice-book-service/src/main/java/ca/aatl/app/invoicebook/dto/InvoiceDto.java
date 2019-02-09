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

}
