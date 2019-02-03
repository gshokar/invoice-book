/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-28  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.Client_;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee_;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeCode;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeCode_;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeEntry;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeEntry_;
import ca.aatl.app.invoicebook.util.AppUtils;
import java.util.Calendar;
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
public class TimeEnityDao extends AbstractDao<TimeEntry> {

    @Override
    public void save(TimeEntry entity) throws Exception {
        if (entity != null) {

            if (entity.getId() == null) {

                create(entity);
            } else {
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(TimeEntry entity) throws Exception {

    }

    @Override
    public void beforeUpdate(TimeEntry entity) throws Exception {

    }

    public TimeEntry find(String guid) {
        TimeEntry entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<TimeEntry> cq = cb.createQuery(TimeEntry.class);

            Root<TimeEntry> root = cq.from(TimeEntry.class);

            cq.select(root);
            cq.where(cb.equal(root.get(TimeEntry_.guid), guid));

            TypedQuery<TimeEntry> q = em.createQuery(cq);

            entity = q.getSingleResult();

        } catch (NoResultException ex) {

        }
        return entity;
    }

    public List<TimeEntry> list() {
        List<TimeEntry> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<TimeEntry> cq = cb.createQuery(TimeEntry.class);

            Root<TimeEntry> root = cq.from(TimeEntry.class);

            cq.select(root);
            cq.orderBy(cb.asc(root.get(TimeEntry_.date)), cb.asc(root.get(TimeEntry_.startTime)));
            
            TypedQuery<TimeEntry> q = em.createQuery(cq);

            list = q.getResultList();
            
        } catch (NoResultException ex) {

        }
        return list;
    }

    public List<TimeEntry> find(String employeeNumber, Date yearMonthDate, String clientNumber) {
        List<TimeEntry> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<TimeEntry> cq = cb.createQuery(TimeEntry.class);

            Root<TimeEntry> root = cq.from(TimeEntry.class);
            Join<TimeEntry, Employee> employee = root.join(TimeEntry_.employee);
            
            cq.select(root);
            
            Predicate predicate = null;
            
            if (!AppUtils.isNullOrEmpty(employeeNumber)) {
                predicate = cb.equal(employee.get(Employee_.number), employeeNumber);
            }
            
            if(yearMonthDate != null){
                Calendar calendar = Calendar.getInstance();
                
                calendar.setTime(yearMonthDate);
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.DATE, -1);
                
                Date lastDayOfMonth = calendar.getTime();
                
                Predicate p = cb.between(root.get(TimeEntry_.date), yearMonthDate, lastDayOfMonth);
                
                if (predicate == null) {
                    predicate = p;
                } else {
                    predicate = cb.and(predicate, p);
                }
            }
            
            if (!AppUtils.isNullOrEmpty(clientNumber)) {
                Join<TimeEntry, TimeCode> timeCode = root.join(TimeEntry_.timeCode);
                Join<TimeCode, Client> client = timeCode.join(TimeCode_.client);
                
                predicate = cb.equal(client.get(Client_.number), clientNumber);
            }
            
            if (predicate != null) {
                cq.where(predicate);
            }
            
            cq.orderBy(cb.asc(root.get(TimeEntry_.date)), cb.asc(root.get(TimeEntry_.startTime)));
            
            TypedQuery<TimeEntry> q = em.createQuery(cq);

            list = q.getResultList();
            
        } catch (NoResultException ex) {

        }
        return list;
    }

}
