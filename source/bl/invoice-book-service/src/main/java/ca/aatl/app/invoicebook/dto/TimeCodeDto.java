/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-23  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class TimeCodeDto {
    
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

    private boolean active;

    /**
     * Get the value of active
     *
     * @return the value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the value of active
     *
     * @param active new value of active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean chargeable;

    /**
     * Get the value of chargeable
     *
     * @return the value of chargeable
     */
    public boolean isChargeable() {
        return chargeable;
    }

    /**
     * Set the value of chargeable
     *
     * @param chargeable new value of chargeable
     */
    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
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

    private ClientLocationDto clientLocation;

    /**
     * Get the value of clientLocation
     *
     * @return the value of clientLocation
     */
    public ClientLocationDto getClientLocation() {
        return clientLocation;
    }

    /**
     * Set the value of clientLocation
     *
     * @param clientLocation new value of clientLocation
     */
    public void setClientLocation(ClientLocationDto clientLocation) {
        this.clientLocation = clientLocation;
    }

    private CompanyServiceDto companyService;

    /**
     * Get the value of companyService
     *
     * @return the value of companyService
     */
    public CompanyServiceDto getCompanyService() {
        return companyService;
    }

    /**
     * Set the value of companyService
     *
     * @param companyService new value of companyService
     */
    public void setCompanyService(CompanyServiceDto companyService) {
        this.companyService = companyService;
    }

}
