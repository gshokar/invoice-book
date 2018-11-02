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
package ca.aatl.app.invoicebook.exception;

/**
 *
 * @author GShokar
 */
public class DataValidationException extends Exception {

    private String errorProperty;
    
    /**
     * Get the value of property
     *
     * @return the value of property
     */
    public String getErrorProperty() {
        return errorProperty;
    }

    /**
     * Set the value of property
     *
     * @param errorProperty new value of property
     */
    public void setErrorProperty(String errorProperty) {
        this.errorProperty = errorProperty;
    }
    private String validationMessage;

    /**
     * Get the value of validationMessage
     *
     * @return the value of validationMessage
     */
    public String getValidationMessage() {
        return validationMessage;
    }

    /**
     * Set the value of validationMessage
     *
     * @param validationMessage new value of validationMessage
     */
    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    /**
     * Creates a new instance of <code>DataValidationException</code> without detail message.
     */
    public DataValidationException() {
    }


    /**
     * Constructs an instance of <code>DataValidationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DataValidationException(String msg) {
        super("Please follow the below validation(s)..." + System.lineSeparator() + msg);
        validationMessage = msg;
    }

    public DataValidationException(String errorProperty, String message) {
        this(message);
        this.errorProperty = errorProperty;
    }
    
}
