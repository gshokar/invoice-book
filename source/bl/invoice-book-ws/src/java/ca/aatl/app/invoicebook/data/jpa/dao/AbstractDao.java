/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-30  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import javax.persistence.EntityManager;

/**
 *
 * @author GShokar
 */
public abstract class AbstractDao {
            
    public final static String  PU_INVOICEBOOK = "invoicebook-pu";
    
    protected final EntityManager em;

    public AbstractDao(EntityManager em) {
        this.em = em;
    }
           
}
