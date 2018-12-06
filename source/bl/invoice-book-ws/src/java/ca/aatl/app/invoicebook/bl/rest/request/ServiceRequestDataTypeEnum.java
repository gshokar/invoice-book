/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-08  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.request;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author GShokar
 */
public enum ServiceRequestDataTypeEnum {
    
    @SerializedName("client")
    Client,
    @SerializedName("clientSearch")
    ClientSearch,
    @SerializedName("provinceList")
    ProvinceList
}
