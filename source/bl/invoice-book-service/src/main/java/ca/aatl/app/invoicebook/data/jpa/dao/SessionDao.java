/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-31  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.AppSession;
import ca.aatl.app.invoicebook.data.jpa.entity.AppSession_;
import ca.aatl.app.invoicebook.data.jpa.entity.AppUser;
import ca.aatl.app.invoicebook.data.jpa.entity.AppUser_;
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
 * @author GShokar
 */

@Stateless
@LocalBean
public class SessionDao extends AbstractDao<AppSession>{
    
    public AppSession find(Integer userId) {
        
        AppSession entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<AppSession> cq = cb.createQuery(AppSession.class);
            Root<AppSession> session = cq.from(AppSession.class);
            Join<AppSession, AppUser> user = session.join(AppSession_.user);
            
            cq.select(session);
            cq.where(cb.equal(user.get(AppUser_.id), userId));
            cq.orderBy(cb.desc(session.get(AppSession_.lastUpdatedDate)));
            
            TypedQuery<AppSession> q = em.createQuery(cq);

            entity =  q.getSingleResult();
            
        } catch (NoResultException ex) {
            entity = null;
        }
        
        return entity;
    }

    @Override
    public void save(AppSession entity) throws Exception {
        
        if(entity.getAddedDate() == null){
            create(entity);
        }else{
            update(entity);
        }
    }

    @Override
    public void beforeCreate(AppSession entity) throws Exception {
        
        entity.setAddedBy(entity.getLastUpdatedBy());
        entity.setAddedDate(entity.getLastUpdatedDate());
        entity.getGuid();
    }

    @Override
    public void beforeUpdate(AppSession entity) throws Exception {
        
    }

    public AppSession find(String sessionId) {
        AppSession entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<AppSession> cq = cb.createQuery(AppSession.class);
            Root<AppSession> session = cq.from(AppSession.class);
                        
            cq.select(session);
            cq.where(cb.equal(session.get(AppSession_.id), sessionId));
            cq.orderBy(cb.desc(session.get(AppSession_.lastUpdatedDate)));
            
            TypedQuery<AppSession> q = em.createQuery(cq);

            entity =  q.getSingleResult();
            
        } catch (NoResultException ex) {
            entity = null;
        }
        
        return entity;
    }
}
