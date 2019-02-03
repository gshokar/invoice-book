/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-02  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Company;
import ca.aatl.app.invoicebook.data.jpa.entity.Company_;
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
public class CompanyDao extends AbstractDao<Company> {

    @Override
    public void save(Company entity) throws Exception {
        if (entity != null) {

            if (entity.getId() == null) {

                create(entity);
            } else {
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(Company entity) throws Exception {
        
    }

    @Override
    public void beforeUpdate(Company entity) throws Exception {
        
    }
    
    public Company find(String number) throws Exception{
        Company entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Company> cq = cb.createQuery(Company.class);

            Root<Company> root = cq.from(Company.class);

            cq.select(root);
            cq.where(cb.equal(root.get(Company_.number), number));

            TypedQuery<Company> q = em.createQuery(cq);

            entity = q.getSingleResult();

            if (entity != null) {
                entity.getAddresses().size();
                entity.getContacts().size();
            }

        } catch (NoResultException ex) {

        }
        return entity;
    }
}
