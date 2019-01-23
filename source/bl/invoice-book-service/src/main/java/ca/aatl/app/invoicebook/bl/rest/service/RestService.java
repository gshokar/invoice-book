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
import ca.aatl.app.invoicebook.bl.rest.response.ErrorResponse;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponse;
import ca.aatl.app.invoicebook.bl.rest.response.ServiceResponseStatusEnum;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 *
 * @author GShokar
 */
public class RestService {
    
    private Gson gson;
    
    protected ServiceRequest request;
    protected ServiceResponse response;
    
    public RestService() {
    }

    public RestService(ServiceRequest request, ServiceResponse response) {
        this.request = request;
        this.response = response;
    }
    
    public void setResponseError(String message) {
        setResponseError(ErrorResponse.CODE_INTERNAL_SERVER_ERROR, message);
    }

    public void setResponseError(int code, String message) {
        response.setStatus(ServiceResponseStatusEnum.Failure);
        response.setMessage(message);
        response.setError(new ErrorResponse(code));
        response.getError().getMessages().add(message);
    }
    
    public void setResponseSuccess(Object data) {
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
    
    protected <T extends Object> T getDto(Class<T> dtoClass, String json)throws JsonSyntaxException{
        
        return getGson().fromJson(json, dtoClass);
    }
    
     public String getResponseJson(){
        return getGson().toJson(getResponse());
    }
}
