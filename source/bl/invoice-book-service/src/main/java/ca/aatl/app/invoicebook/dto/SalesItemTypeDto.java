/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-14  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class SalesItemTypeDto {
    
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

}
