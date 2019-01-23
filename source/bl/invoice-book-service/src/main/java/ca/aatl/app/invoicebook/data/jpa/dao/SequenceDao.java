/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-15  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.base.Sequence;
import ca.aatl.app.invoicebook.data.jpa.entity.base.Sequence_;
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
public class SequenceDao extends BaseDao{
    
    public synchronized Sequence find(String name) {
        Sequence entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Sequence> cq = cb.createQuery(Sequence.class);
            Root<Sequence> sequence = cq.from(Sequence.class);

            cq.select(sequence);
            cq.where(cb.equal(sequence.get(Sequence_.name), name));

            TypedQuery<Sequence> q = em.createQuery(cq);
            
            entity =  q.getSingleResult();

        } catch (NoResultException ex) {
        }

        return entity;
    }
    
    public synchronized void save(Sequence entity) {
        
        if(entity != null){
            em.merge(entity);
        }
    }
}
