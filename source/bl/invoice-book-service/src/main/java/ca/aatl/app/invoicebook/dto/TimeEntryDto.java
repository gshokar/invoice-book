/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-28  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class TimeEntryDto {
    
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

    private String startTime;

    /**
     * Get the value of startTime
     *
     * @return the value of startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Set the value of startTime
     *
     * @param startTime new value of startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    private String endTime;

    /**
     * Get the value of endTime
     *
     * @return the value of endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Set the value of endTime
     *
     * @param endTime new value of endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private double hours;

    /**
     * Get the value of hours
     *
     * @return the value of hours
     */
    public double getHours() {
        return hours;
    }

    /**
     * Set the value of hours
     *
     * @param hours new value of hours
     */
    public void setHours(double hours) {
        this.hours = hours;
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

    private TimeCodeDto timeCode;

    /**
     * Get the value of timeCode
     *
     * @return the value of timeCode
     */
    public TimeCodeDto getTimeCode() {
        return timeCode;
    }

    /**
     * Set the value of timeCode
     *
     * @param timeCode new value of timeCode
     */
    public void setTimeCode(TimeCodeDto timeCode) {
        this.timeCode = timeCode;
    }

}
