/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-30  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Country;
import ca.aatl.app.invoicebook.data.jpa.entity.Country_;
import ca.aatl.app.invoicebook.data.jpa.entity.Province;
import ca.aatl.app.invoicebook.data.jpa.entity.Province_;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemTaxRate;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemTaxRate_;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItem_;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author gshokar
 */
public class SalesItemTaxRateDao extends AbstractDao<SalesItemTaxRate> {

    public List<SalesItemTaxRate> list(Integer salesItemId, Date date, Integer countryId, Integer provinceId) {
        List<SalesItemTaxRate> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesItemTaxRate> cq = cb.createQuery(SalesItemTaxRate.class);

            Root<SalesItemTaxRate> root = cq.from(SalesItemTaxRate.class);

            Join<SalesItemTaxRate, SalesItem> item = root.join(SalesItemTaxRate_.item);

            cq.select(root);

            Predicate predicate = cb.and(cb.equal(item.get(SalesItem_.id), salesItemId),
                    cb.greaterThanOrEqualTo(root.get(SalesItemTaxRate_.fromDate), date));

            if (countryId != null && countryId > 0) {
                Join<SalesItemTaxRate, Country> country = root.join(SalesItemTaxRate_.country);

                predicate = cb.and(predicate, cb.equal(country.get(Country_.id), countryId));
            }

            if (provinceId != null && provinceId > 0) {
                Join<SalesItemTaxRate, Province> province = root.join(SalesItemTaxRate_.Province);

                predicate = cb.and(predicate, cb.equal(province.get(Province_.id), provinceId));
            }

            cq.where(predicate);
            cq.orderBy(cb.desc(root.get(SalesItemTaxRate_.fromDate)));

            TypedQuery<SalesItemTaxRate> query = em.createQuery(cq);

            list = query.getResultList();
            
        } catch (NoResultException ex) {
        }

        return list;
    }

    @Override
    public void save(SalesItemTaxRate entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void beforeCreate(SalesItemTaxRate entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void beforeUpdate(SalesItemTaxRate entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
