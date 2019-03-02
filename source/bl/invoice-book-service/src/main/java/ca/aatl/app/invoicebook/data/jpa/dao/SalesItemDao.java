/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-09  GShokar         Created
 * =============================================================================
 */

package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.SalesItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemType;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemType_;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItem_;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author gshokar
 */

@Stateless
@LocalBean
public class SalesItemDao extends BaseDao{
    
     public List<SalesItem> list() throws Exception{
        
        List<SalesItem> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesItem> cq = cb.createQuery(SalesItem.class);

            Root<SalesItem> root = cq.from(SalesItem.class);

            cq.select(root);
            cq.orderBy(cb.asc(root.get(SalesItem_.name)));

            TypedQuery<SalesItem> q = em.createQuery(cq);

            list =  q.getResultList();
            
        } catch (NoResultException ex) {

        }
        
        return list;
    }

     public List<SalesItem> listByItemType(String itemTypeName) throws Exception{
        
        List<SalesItem> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesItem> cq = cb.createQuery(SalesItem.class);

            Root<SalesItem> root = cq.from(SalesItem.class);
            Join<SalesItem, SalesItemType> itemType = root.join(SalesItem_.itemType);
            
            cq.select(root);
            cq.where(cb.equal(itemType.get(SalesItemType_.name), itemTypeName));
            cq.orderBy(cb.asc(root.get(SalesItem_.name)));

            TypedQuery<SalesItem> q = em.createQuery(cq);

            list =  q.getResultList();
            
        } catch (NoResultException ex) {

        }
        
        return list;
    }
     
    public SalesItem find(String code) throws Exception{
        
        SalesItem entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesItem> cq = cb.createQuery(SalesItem.class);

            Root<SalesItem> root = cq.from(SalesItem.class);

            cq.select(root);
            cq.where(cb.equal(root.get(SalesItem_.code), code));

            TypedQuery<SalesItem> q = em.createQuery(cq);

            entity =  q.getSingleResult();
            
        } catch (NoResultException ex) {

        }
        
        return entity;
    }

    public List<SalesItemType> itemTypes() {
        List<SalesItemType> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesItemType> cq = cb.createQuery(SalesItemType.class);

            Root<SalesItemType> root = cq.from(SalesItemType.class);
          
            cq.select(root);
            cq.orderBy(cb.asc(root.get(SalesItemType_.name)));

            TypedQuery<SalesItemType> q = em.createQuery(cq);

            list =  q.getResultList();
            
        } catch (NoResultException ex) {

        }
        
        return list;
    }
}
