/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-23  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoice;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoice_;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author GShokar
 */

@Stateless
@LocalBean
public class SalesInvoiceDao extends AbstractDao<SalesInvoice>{

    @Override
    public void save(SalesInvoice entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void beforeCreate(SalesInvoice entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void beforeUpdate(SalesInvoice entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public SalesInvoice find(String number) throws Exception{
        
        SalesInvoice entity = null;
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesInvoice> cq = cb.createQuery(SalesInvoice.class);

            Root<SalesInvoice> root = cq.from(SalesInvoice.class);

            cq.select(root);
            cq.where(cb.equal(root.get(SalesInvoice_.number), number));

            TypedQuery<SalesInvoice> q = em.createQuery(cq);

            entity = q.getSingleResult();

        } catch (NoResultException ex) {

        }
        return entity;
    }

}
