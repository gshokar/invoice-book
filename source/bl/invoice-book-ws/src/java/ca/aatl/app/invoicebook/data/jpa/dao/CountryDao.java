/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-14  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Country;
import ca.aatl.app.invoicebook.data.jpa.entity.base.CountryEntity_;
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
public class CountryDao extends BaseDao{

    public Country find(String code) {
        Country entity = null;
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Country> cq = cb.createQuery(Country.class);

            Root<Country> root = cq.from(Country.class);

            cq.select(root);
            cq.where(cb.equal(root.get(CountryEntity_.code), code));

            TypedQuery<Country> q = em.createQuery(cq);

            entity =  q.getSingleResult();
            
        } catch (NoResultException ex) {

        }
        return entity;
    }
    
}
