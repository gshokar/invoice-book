/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-09  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientLocation;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientLocation_;
import ca.aatl.app.invoicebook.data.jpa.entity.Client_;
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
public class ClientLocationDao extends AbstractDao<ClientLocation> {

    @Override
    public void save(ClientLocation entity) throws Exception {
        if (entity != null) {

            if (entity.getId() == null) {

                create(entity);
            } else {
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(ClientLocation entity) throws Exception {

    }

    @Override
    public void beforeUpdate(ClientLocation entity) throws Exception {

    }

    public List<ClientLocation> list(String clientNumber) {
        List<ClientLocation> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<ClientLocation> cq = cb.createQuery(ClientLocation.class);

            Root<ClientLocation> root = cq.from(ClientLocation.class);
            Join<ClientLocation, Client> client = root.join(ClientLocation_.client);

            cq.select(root);
            cq.where(cb.equal(client.get(Client_.number), clientNumber));

            TypedQuery<ClientLocation> q = em.createQuery(cq);

            list = q.getResultList();

        } catch (NoResultException ex) {

        }

        return list;
    }

    public boolean isExists(Integer id, String number, String name, Integer clientId) {
        boolean value = false;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<ClientLocation> root = cq.from(ClientLocation.class);
            Join<ClientLocation, Client> client = root.join(ClientLocation_.client);

            cq.select(cb.count(root));

            Predicate predicate = cb.and(cb.equal(client.get(Client_.id), clientId),
                    cb.equal(root.get(ClientLocation_.name), name));

            if (id != null) {
                predicate = cb.and(predicate,
                        cb.notEqual(root.get(ClientLocation_.id), id));
            }

            if (number != null) {
                predicate = cb.and(predicate,
                        cb.notEqual(root.get(ClientLocation_.number), number));
            }

            cq.where(predicate);

            TypedQuery<Long> q = em.createQuery(cq);

            value = q.getSingleResult() != null && q.getSingleResult() > 0;

        } catch (NoResultException ex) {
        }

        return value;
    }

    public ClientLocation find(String number) throws Exception{
        
        ClientLocation entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<ClientLocation> cq = cb.createQuery(ClientLocation.class);

            Root<ClientLocation> root = cq.from(ClientLocation.class);

            cq.select(root);
            cq.where(cb.equal(root.get(ClientLocation_.number), number));

            TypedQuery<ClientLocation> q = em.createQuery(cq);

            entity = q.getSingleResult();
            
        } catch (NoResultException ex) {

        }
        
        return entity;
    }

}
