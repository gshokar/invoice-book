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
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.rest.request.ServiceRequestDataTypeEnum;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;

/**
 *
 * @author GShokar
 */
class ServiceHandler {

    private final static String JAVA_MODULE_LOOKUP_FORMAT = "java:module/$1s";
    private final Map<ServiceRequestDataTypeEnum, String> serviceMapper = new HashMap<>();
    
    private static ServiceHandler handler;
    
    private ServiceHandler(){
        registerServices();
    }
    static ServiceHandler getInstance(){
        
        if(handler == null){
            handler = new ServiceHandler();
        }
        
        return handler;
    }
    
    ResponseService getService(ServiceRequestDataTypeEnum dataType) throws Exception {
        
        if(!serviceMapper.containsKey(dataType)){
            throw new Exception("Unregistered service: " + dataType.name());
        }
        
        return InitialContext.doLookup(String.format(JAVA_MODULE_LOOKUP_FORMAT, serviceMapper.get(dataType)));
    }

    private void registerServices(){
        serviceMapper.put(ServiceRequestDataTypeEnum.Client, "ClientResponseService");
        serviceMapper.put(ServiceRequestDataTypeEnum.ClientSearch, "ClientResponseService");
        serviceMapper.put(ServiceRequestDataTypeEnum.ProvinceList, "LookupResponseService");
    }
}
