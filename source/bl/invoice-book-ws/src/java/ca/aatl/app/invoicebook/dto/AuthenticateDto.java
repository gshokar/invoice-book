/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-26  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class AuthenticateDto {

    private String loginId;

    /**
     * Get the value of loginId
     *
     * @return the value of loginId
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Set the value of loginId
     *
     * @param loginId new value of loginId
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    private String password;

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
