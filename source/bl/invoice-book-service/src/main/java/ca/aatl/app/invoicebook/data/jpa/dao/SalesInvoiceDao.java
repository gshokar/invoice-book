/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-23  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.Client_;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoice;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItemTax;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceStatus;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceStatus_;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoice_;
import ca.aatl.app.invoicebook.util.AppUtils;
import java.util.ArrayList;
import java.util.Date;
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
public class SalesInvoiceDao extends AbstractDao<SalesInvoice> {

    @Override
    public void save(SalesInvoice entity) throws Exception {
        if (entity != null) {

            if (entity.getId() == null) {

                create(entity);
            } else {
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(SalesInvoice entity) throws Exception {

    }

    @Override
    public void beforeUpdate(SalesInvoice entity) throws Exception {

        if (entity.getItems() != null) {
            for (SalesInvoiceItem sii : entity.getItems()) {

                if (sii.getId() == null) {
                    em.persist(sii);
                } else {

                    if (sii.getTaxes() != null) {
                        for (SalesInvoiceItemTax siit : sii.getTaxes()) {

                            if (siit.getId() == null) {
                                em.persist(siit);
                            } else {
                                em.merge(siit);
                            }
                        }
                    }
                    em.merge(sii);
                }
            }
        }
    }

    public SalesInvoice find(String number) throws Exception {

        SalesInvoice entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesInvoice> cq = cb.createQuery(SalesInvoice.class);

            Root<SalesInvoice> root = cq.from(SalesInvoice.class);

            cq.select(root);
            cq.where(cb.equal(root.get(SalesInvoice_.number), number));

            TypedQuery<SalesInvoice> q = em.createQuery(cq);

            entity = q.getSingleResult();

        } catch (NoResultException ex) {

        }
        return entity;
    }

    public SalesInvoiceStatus invoiceStatus(String name) throws Exception {
        SalesInvoiceStatus entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesInvoiceStatus> cq = cb.createQuery(SalesInvoiceStatus.class);

            Root<SalesInvoiceStatus> root = cq.from(SalesInvoiceStatus.class);

            cq.select(root);
            cq.where(cb.equal(root.get(SalesInvoiceStatus_.name), name));

            TypedQuery<SalesInvoiceStatus> q = em.createQuery(cq);

            entity = q.getSingleResult();

        } catch (NoResultException ex) {

        }
        return entity;
    }

    public List<SalesInvoice> find(String number, Date date, String clientNumber) {
        List<SalesInvoice> list = new ArrayList<>();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<SalesInvoice> cq = cb.createQuery(SalesInvoice.class);

            Root<SalesInvoice> root = cq.from(SalesInvoice.class);

            cq.select(root);

            Predicate predicate = null;

            if (!AppUtils.isNullOrEmpty(number)) {
                predicate = cb.equal(root.get(SalesInvoice_.number), number);
            }

            if (date != null) {
                Predicate predicateDate = cb.equal(root.get(SalesInvoice_.date), date);

                if (predicate == null) {
                    predicate = predicateDate;
                } else {
                    predicate = cb.and(predicate, predicateDate);
                }
            }

            if (!AppUtils.isNullOrEmpty(clientNumber)) {
                Join<SalesInvoice, Client> client = root.join(SalesInvoice_.client);

                Predicate predicateClientNumber = cb.equal(client.get(Client_.number), clientNumber);

                if (predicate == null) {
                    predicate = predicateClientNumber;
                } else {
                    predicate = cb.and(predicate, predicateClientNumber);
                }
            }

            if (predicate != null) {
                cq.where(predicate);
            }
            
            TypedQuery<SalesInvoice> q = em.createQuery(cq);

            list = q.getResultList();

        } catch (NoResultException ex) {

        }
        return list;
    }

}
