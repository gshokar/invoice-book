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

import java.util.List;

/**
 *
 * @author GShokar
 */
public class TimeSheetDto {
    
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

    private EmployeeDto employee;

    /**
     * Get the value of employee
     *
     * @return the value of employee
     */
    public EmployeeDto getEmployee() {
        return employee;
    }

    /**
     * Set the value of employee
     *
     * @param employee new value of employee
     */
    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
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

    private String month;

    /**
     * Get the value of month
     *
     * @return the value of month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Set the value of month
     *
     * @param month new value of month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    private List<TimeEntryDto> timeEntries;

    /**
     * Get the value of timeEntries
     *
     * @return the value of timeEntries
     */
    public List<TimeEntryDto> getTimeEntries() {
        return timeEntries;
    }

    /**
     * Set the value of timeEntries
     *
     * @param timeEntries new value of timeEntries
     */
    public void setTimeEntries(List<TimeEntryDto> timeEntries) {
        this.timeEntries = timeEntries;
    }

}
