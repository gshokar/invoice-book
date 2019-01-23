/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-26  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author GShokar
 */
@javax.ws.rs.ApplicationPath("/api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ca.aatl.app.invoicebook.bl.rest.AuthenticatingFilter.class);
        resources.add(ca.aatl.app.invoicebook.bl.rest.CORSFilter.class);
        resources.add(ca.aatl.app.invoicebook.bl.rest.RequestAuthenticationFilter.class);
        resources.add(ca.aatl.app.invoicebook.bl.rest.service.AuthenticationResourceService.class);
        resources.add(ca.aatl.app.invoicebook.bl.rest.service.ClientLocationResourceService.class);
        resources.add(ca.aatl.app.invoicebook.bl.rest.service.ClientResourceService.class);
        resources.add(ca.aatl.app.invoicebook.bl.rest.service.EmployeeResourceService.class);
        resources.add(ca.aatl.app.invoicebook.bl.rest.service.LookupResourceService.class);
    }
    
}
