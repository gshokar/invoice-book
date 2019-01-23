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

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author GShokar
 */
public enum ServiceRequestTypeEnum {
    @SerializedName("authenticate")
    Authenticate,
    @SerializedName("update")
    Update,
    @SerializedName("get")
    Get
}
