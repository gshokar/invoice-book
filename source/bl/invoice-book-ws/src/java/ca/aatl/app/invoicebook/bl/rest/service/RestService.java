/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-01  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequest;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponseStatusEnum;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;

/**
 *
 * @author GShokar
 */
public class RestService {
    
    private Gson gson;
    
    private ServiceRequest request;
    private ServiceResponse response;
    
    public RestService() {
    }

    public RestService(ServiceRequest request, ServiceResponse response) {
        this.request = request;
        this.response = response;
    }
    
    protected void setResponseError(String message) {
        response.setStatus(ServiceResponseStatusEnum.Failure);
        response.setMessage(message);
    }

    protected void setResponseSuccess(Object data) {
        response.setStatus(ServiceResponseStatusEnum.Success);
        if (data != null) {
            response.setData(getGson().toJson(data));
        }
    }

    protected Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
        
    public ServiceRequest getRequest() {
        return request;
    }

    public ServiceResponse getResponse() {
        return response;
    }

    public void setRequest(ServiceRequest request) {
        this.request = request;
    }

    public void setResponse(ServiceResponse response) {
        this.response = response;
    }

    protected <T extends Object> T getDto(Class<T> dtoClass)throws JsonSyntaxException{
        
        return getGson().fromJson(request.getData(), dtoClass);
    }
    
    protected void addWarningMessage(String message) {
        
        if(response.getWarningMessages() == null){
            response.setWarningMessages(new ArrayList<>());
        }
        
        response.getWarningMessages().add(message);
    }
}