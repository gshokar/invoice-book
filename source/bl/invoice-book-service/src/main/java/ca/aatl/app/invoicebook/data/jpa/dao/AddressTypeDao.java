/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-13  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.AddressType;
import ca.aatl.app.invoicebook.data.jpa.entity.base.TypeEntity_;
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
public class AddressTypeDao extends BaseDao{
   
    public AddressType find(String name) {
        AddressType entity = null;
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<AddressType> cq = cb.createQuery(AddressType.class);

            Root<AddressType> root = cq.from(AddressType.class);

            cq.select(root);
            cq.where(cb.equal(root.get(TypeEntity_.name), name));

            TypedQuery<AddressType> q = em.createQuery(cq);

            entity =  q.getSingleResult();
            
        } catch (NoResultException ex) {

        }
        return entity;
    }
    
}
