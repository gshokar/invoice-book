/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-23  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.Client_;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeCode;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeCode_;
import ca.aatl.app.invoicebook.util.AppUtils;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
public class TimeCodeDao extends AbstractDao<TimeCode> {

    @Override
    public void save(TimeCode entity) throws Exception {
        if (entity != null) {

            if (entity.getId() == null) {

                create(entity);
            } else {
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(TimeCode entity) throws Exception {

    }

    @Override
    public void beforeUpdate(TimeCode entity) throws Exception {

    }

    public boolean isExists(Integer id, String guid, String name) {
        boolean value = false;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<TimeCode> root = cq.from(TimeCode.class);

            cq.select(cb.count(root));

            Predicate predicate = cb.equal(root.get(TimeCode_.name),
                    name);

            if (id != null) {
                predicate = cb.and(predicate,
                        cb.notEqual(root.get(TimeCode_.id), id));
            }

            if (guid != null) {
                predicate = cb.and(predicate,
                        cb.notEqual(root.get(TimeCode_.guid), guid));
            }

            cq.where(predicate);

            TypedQuery<Long> q = em.createQuery(cq);

            value = q.getSingleResult() != null && q.getSingleResult() > 0;

        } catch (NoResultException ex) {
        }

        return value;
    }

    public TimeCode findByGuid(String guid) throws Exception {
        TimeCode entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<TimeCode> cq = cb.createQuery(TimeCode.class);

            Root<TimeCode> root = cq.from(TimeCode.class);

            cq.select(root);
            cq.where(cb.equal(root.get(TimeCode_.guid), guid));

            TypedQuery<TimeCode> q = em.createQuery(cq);

            entity = q.getSingleResult();

        } catch (NoResultException ex) {

        }
        return entity;
    }

    public List<TimeCode> list() throws Exception {
        List<TimeCode> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<TimeCode> cq = cb.createQuery(TimeCode.class);

            Root<TimeCode> root = cq.from(TimeCode.class);

            cq.select(root);
            cq.orderBy(cb.asc(root.get(TimeCode_.name)));

            TypedQuery<TimeCode> q = em.createQuery(cq);

            list = q.getResultList();

        } catch (NoResultException ex) {

        }
        return list;
    }

    public List<TimeCode> find(String clientNumber) throws Exception {
        List<TimeCode> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<TimeCode> cq = cb.createQuery(TimeCode.class);

            Root<TimeCode> root = cq.from(TimeCode.class);
            Predicate predicate = null;

            if (!AppUtils.isNullOrEmpty(clientNumber)) {
                Join<TimeCode, Client> client = root.join(TimeCode_.client);
                predicate = cb.equal(client.get(Client_.number), clientNumber);
            }
            cq.select(root);
            
            if (predicate != null) {
                cq.where(predicate);
            }
            
            cq.orderBy(cb.asc(root.get(TimeCode_.name)));

            TypedQuery<TimeCode> q = em.createQuery(cq);

            list = q.getResultList();

        } catch (NoResultException ex) {

        }
        return list;
    }
}
