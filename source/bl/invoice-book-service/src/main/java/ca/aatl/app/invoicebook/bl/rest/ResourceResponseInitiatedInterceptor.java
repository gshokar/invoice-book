/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-11  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest;

import ca.aatl.app.invoicebook.bl.rest.service.ResponseService;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author GShokar
 */
@ResourceResponseInitiated
@Interceptor
public class ResourceResponseInitiatedInterceptor {
    
    @AroundInvoke
    public Object initializeResponse(InvocationContext ctx) throws Exception {
        
        if(ctx.getTarget() instanceof ResponseService){
            ResponseService rs = (ResponseService)ctx.getTarget();
            
            rs.clearWarningMessages();
        }
        return ctx.proceed();
    }
}
