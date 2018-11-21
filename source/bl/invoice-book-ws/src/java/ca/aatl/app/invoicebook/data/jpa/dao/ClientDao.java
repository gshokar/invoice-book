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
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author GShokar
 */

@Stateless
@LocalBean
public class ClientDao extends AbstractDao<Client>{
    
    @Override
    public void save(Client entity) throws Exception {
        if(entity != null){
            
            if(entity.getId() == null){
                
                create(entity);
            }else{
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(Client entity) throws Exception {
        entity.setAddedBy(entity.getLastUpdatedBy());
        entity.setAddedDate(entity.getLastUpdatedDate());
        entity.getGuid();
    }

    @Override
    public void beforeUpdate(Client entity) throws Exception {
        
    }
    
}
