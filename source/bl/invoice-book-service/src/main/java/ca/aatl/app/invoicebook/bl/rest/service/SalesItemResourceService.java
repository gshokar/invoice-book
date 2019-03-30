/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-16  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.SalesItemService;
import ca.aatl.app.invoicebook.bl.rest.Authenticated;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemType;
import ca.aatl.app.invoicebook.data.service.MappingService;
import ca.aatl.app.invoicebook.dto.SalesItemDto;
import ca.aatl.app.invoicebook.dto.SalesItemTypeDto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
@Path("sales-item")
public class SalesItemResourceService extends ResponseService {

    @EJB
    SalesItemService salesItemService;

    @EJB
    MappingService mappingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/service-items")
    public String serviceItems() {

        try {
            List<SalesItem> entityList = salesItemService.serviceItems();

            List<SalesItemDto> dtoList = new ArrayList<>();

            if (entityList != null && !entityList.isEmpty()) {

                entityList.forEach(entity -> dtoList.add(createSalesItemDto(entity)));
            }

            this.setResponseSuccess(dtoList);

        } catch (Exception ex) {

            setResponseError("System error failed to get the service item list - " + ex.getMessage());

            Logger.getLogger(LookupResourceService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.getResponseJson();
    }

    private SalesItemDto createSalesItemDto(SalesItem entity) {
        SalesItemDto dto = new SalesItemDto();
        mappingService.updateSalesItemDto(dto, entity);
        return dto;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/items-by-item-type")
    @Authenticated
    public String itemsByItemType(@QueryParam("itemType") String itemTypeName) {

        try {
            List<SalesItem> entityList = salesItemService.itemsByItemType(itemTypeName);

            List<SalesItemDto> dtoList = new ArrayList<>();

            if (entityList != null && !entityList.isEmpty()) {

                entityList.forEach(entity -> dtoList.add(createSalesItemDto(entity)));
            }

            this.setResponseSuccess(dtoList);

        } catch (Exception ex) {

            setResponseError("System error failed to get the item list by item type - " + ex.getMessage());

            Logger.getLogger(LookupResourceService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.getResponseJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/item-types")
    public String salesItemTypes() {

        try {
            List<SalesItemType> entities = salesItemService.itemTypes();

            List<SalesItemTypeDto> dtoList = new ArrayList<>();

            if (entities != null && !entities.isEmpty()) {

                entities.forEach(e -> dtoList.add(new SalesItemTypeDto(e.getGuid(), e.getName())));
            }

            this.setResponseSuccess(dtoList);

        } catch (Exception ex) {

            setResponseError("System error failed to get the sales item type list - " + ex.getMessage());

            Logger.getLogger(LookupResourceService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.getResponseJson();
    }
}
