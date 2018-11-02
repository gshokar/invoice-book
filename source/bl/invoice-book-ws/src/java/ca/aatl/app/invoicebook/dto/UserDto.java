/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-31  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class UserDto {
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
    private String sessionId;

    /**
     * Get the value of sessionId
     *
     * @return the value of sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Set the value of sessionId
     *
     * @param sessionId new value of sessionId
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

}
