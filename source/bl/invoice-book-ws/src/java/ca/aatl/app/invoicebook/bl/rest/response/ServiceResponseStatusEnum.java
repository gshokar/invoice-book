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

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author gshokar
 */
public enum ServiceResponseStatusEnum {
    @SerializedName("success")
    Success, 
    @SerializedName("failure")
    Failure
}
