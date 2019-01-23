/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-09  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class ClientLocationDto extends CommonDto{
    
    private String clientNumber;

    /**
     * Get the value of clientNumber
     *
     * @return the value of clientNumber
     */
    public String getClientNumber() {
        return clientNumber;
    }

    /**
     * Set the value of clientNumber
     *
     * @param clientNumber new value of clientNumber
     */
    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

}
