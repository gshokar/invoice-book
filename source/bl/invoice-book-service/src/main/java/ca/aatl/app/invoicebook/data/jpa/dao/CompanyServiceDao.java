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

import ca.aatl.app.invoicebook.data.jpa.entity.CompanyService;
import ca.aatl.app.invoicebook.data.jpa.entity.CompanyService_;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author gshokar
 */

@Stateless
@LocalBean
public class CompanyServiceDao extends BaseDao{
    
     public List<CompanyService> list() throws Exception{
        
        List<CompanyService> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<CompanyService> cq = cb.createQuery(CompanyService.class);

            Root<CompanyService> root = cq.from(CompanyService.class);

            cq.select(root);
            cq.orderBy(cb.asc(root.get(CompanyService_.name)));

            TypedQuery<CompanyService> q = em.createQuery(cq);

            list =  q.getResultList();
            
        } catch (NoResultException ex) {

        }
        
        return list;
    }

    public CompanyService find(String guid) throws Exception{
        
        CompanyService entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<CompanyService> cq = cb.createQuery(CompanyService.class);

            Root<CompanyService> root = cq.from(CompanyService.class);

            cq.select(root);
            cq.where(cb.equal(root.get(CompanyService_.guid), guid));

            TypedQuery<CompanyService> q = em.createQuery(cq);

            entity =  q.getSingleResult();
            
        } catch (NoResultException ex) {

        }
        
        return entity;
    }
}
