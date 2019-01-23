/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-01  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import java.util.List;

/**
 *
 * @author GShokar
 */

public abstract class AbstractDao<T> extends BaseDao{
      
    public abstract void save(T entity) throws Exception;

    public abstract void beforeCreate(T entity) throws Exception;

    public abstract void beforeUpdate(T entity) throws Exception;

    public void save(T entity, List<Object> deleteEntities) throws Exception {
        try {

            if (deleteEntities != null && !deleteEntities.isEmpty()) {
                this.delete(deleteEntities);
            }

            this.save(entity);

        } catch (Exception ex) {
//            this.rollback();
            throw ex;
        } 
    }

    public void save(List<T> entities, List<Object> deleteEntities) throws Exception {

        try {

            if (deleteEntities != null && !deleteEntities.isEmpty()) {
                this.delete(deleteEntities);
            }

            if (entities != null && !entities.isEmpty()) {
                for (T entity : entities) {
                    save(entity);
                }
            }

        } catch (Exception ex) {
//            this.rollback();
            throw ex;
        }
    }

    public void beforeDelete(Object entity) throws Exception {

    }

    public void create(T entity) throws Exception {

        beforeCreate(entity);

        em.persist(entity);
    }

    public void update(T entity) throws Exception {

        beforeUpdate(entity);

        em.merge(entity);
    }

    public void delete(Object entity) throws Exception {

        beforeDelete(entity);

        em.remove(em.merge(entity));
    }

    public void delete(List<Object> entities) throws Exception {
        if (entities != null && !entities.isEmpty()) {
            for (Object o : entities) {
                delete(o);
            }
        }
    }
    
    public Long count(Class<T> entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return (Long)q.getSingleResult();
    }
}
