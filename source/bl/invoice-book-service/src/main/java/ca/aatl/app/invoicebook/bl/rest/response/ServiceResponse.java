/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-28  GShokar         Created
 * =============================================================================
 */

package ca.aatl.app.invoicebook.bl.rest.response;

import java.util.List;

/**
 *
 * @author gshokar
 */
public class ServiceResponse {
    
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

    private ServiceResponseStatusEnum status;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public ServiceResponseStatusEnum getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(ServiceResponseStatusEnum status) {
        this.status = status;
    }

    private String message;

    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the value of message
     *
     * @param message new value of message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private String data;

    /**
     * Get the value of data
     *
     * @return the value of data
     */
    public String getData() {
        return data;
    }

    /**
     * Set the value of data
     *
     * @param data new value of data
     */
    public void setData(String data) {
        this.data = data;
    }

    private List<String> warningMessages;

    /**
     * Get the value of warningMessages
     *
     * @return the value of warningMessages
     */
    public List<String> getWarningMessages() {
        return warningMessages;
    }

    /**
     * Set the value of warningMessages
     *
     * @param warningMessages new value of warningMessages
     */
    public void setWarningMessages(List<String> warningMessages) {
        this.warningMessages = warningMessages;
    }

    private ErrorResponse error;

    /**
     * Get the value of error
     *
     * @return the value of error
     */
    public ErrorResponse getError() {
        return error;
    }

    /**
     * Set the value of error
     *
     * @param error new value of error
     */
    public void setError(ErrorResponse error) {
        this.error = error;
    }

}
