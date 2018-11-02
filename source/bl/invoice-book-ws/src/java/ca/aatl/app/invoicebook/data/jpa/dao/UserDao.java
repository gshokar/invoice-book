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

import ca.aatl.app.invoicebook.data.jpa.entity.AppUser;
import ca.aatl.app.invoicebook.data.jpa.entity.AppUser_;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
public class UserDao extends AbstractFacadeDao<AppUser>{

//    public UserDao(EntityManager em) {
//        super(em);
//    }

//    @PersistenceContext(unitName = AbstractDao.PU_INVOICEBOOK)
//    private EntityManager em;
            
    public AppUser find(String loginId) {
        AppUser entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<AppUser> cq = cb.createQuery(AppUser.class);
            Root<AppUser> user = cq.from(AppUser.class);
            cq.select(user);
            cq.where(cb.equal(user.get(AppUser_.loginId), loginId));
            TypedQuery<AppUser> q = em.createQuery(cq);

            entity =  q.getSingleResult();
        } catch (NoResultException ex) {
            entity = null;
        }
        
        return entity;
    }

    @Override
    public void save(AppUser entity) throws Exception {
        
        if(entity != null){
            
            if(entity.getId() == null){
                
                create(entity);
            }else{
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(AppUser entity) throws Exception {
        
        entity.setAddedBy(entity.getLastUpdatedBy());
        entity.setAddedDate(entity.getLastUpdatedDate());
        entity.getGuid();
    }

    @Override
    public void beforeUpdate(AppUser entity) throws Exception {
        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
