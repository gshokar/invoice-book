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
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.SalesItemDao;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemType;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author GShokar
 */

@Stateless
@LocalBean
public class SalesItemService {

    @EJB
    SalesItemDao dao;
    
    public List<SalesItem> serviceItems() throws Exception{
        return dao.listByItemType("Service");
    }

    public SalesItem find(String code) throws Exception{
        return dao.find(code);
    }

    public List<SalesItemType> itemTypes() throws Exception{
        return dao.itemTypes();
    }
    
}
