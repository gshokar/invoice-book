/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-27  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.request;

/**
 *
 * @author GShokar
 */
public class ServiceRequest {
    
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

    private String dataType;

    /**
     * Get the value of dataType
     *
     * @return the value of dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Set the value of dataType
     *
     * @param dataType new value of dataType
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    private ServiceRequestTypeEnum requestType;

    /**
     * Get the value of requestType
     *
     * @return the value of requestType
     */
    public ServiceRequestTypeEnum getRequestType() {
        return requestType;
    }

    /**
     * Set the value of requestType
     *
     * @param requestType new value of requestType
     */
    public void setRequestType(ServiceRequestTypeEnum requestType) {
        this.requestType = requestType;
    }

    private String clientIP;

    /**
     * Get the value of clientIP
     *
     * @return the value of clientIP
     */
    public String getClientIP() {
        return clientIP;
    }

    /**
     * Set the value of clientIP
     *
     * @param clientIP new value of clientIP
     */
    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

}
