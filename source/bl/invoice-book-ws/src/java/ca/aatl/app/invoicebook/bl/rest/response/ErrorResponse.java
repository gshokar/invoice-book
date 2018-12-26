/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-25  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.response;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GShokar
 */
public class ErrorResponse {

    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_BAD_REQUEST = 401;
    public static final int CODE_INTERNAL_SERVER_ERROR = 500;

    public ErrorResponse() {
    }

    public ErrorResponse(int code) {
        this.code = code;
    }
        
    private int code;

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(int code) {
        this.code = code;
    }

    private List<String> messages;

    /**
     * Get the value of messages
     *
     * @return the value of messages
     */
    public List<String> getMessages() {

        if (messages == null) {
            messages = new ArrayList<>();
        }

        return messages;
    }

    /**
     * Set the value of messages
     *
     * @param messages new value of messages
     */
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

}
