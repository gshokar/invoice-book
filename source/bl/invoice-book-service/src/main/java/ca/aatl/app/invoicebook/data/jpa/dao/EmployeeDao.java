/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-25  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Contact;
import ca.aatl.app.invoicebook.data.jpa.entity.Contact_;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee;
import ca.aatl.app.invoicebook.data.jpa.entity.Employee_;
import ca.aatl.app.invoicebook.util.AppUtils;
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
public class EmployeeDao extends AbstractDao<Employee> {

    @Override
    public void save(Employee entity) throws Exception {
        if (entity != null) {

            if (entity.getId() == null) {

                create(entity);
            } else {
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(Employee entity) throws Exception {

    }

    @Override
    public void beforeUpdate(Employee entity) throws Exception {

    }

    public Employee find(String number) {

        Employee entity = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

            Root<Employee> root = cq.from(Employee.class);

            cq.select(root);
            cq.where(cb.equal(root.get(Employee_.number), number));

            TypedQuery<Employee> q = em.createQuery(cq);

            entity = q.getSingleResult();

        } catch (NoResultException ex) {

        }
        return entity;
    }

    public List<Employee> find(String name, String phone) {
        List<Employee> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

            Root<Employee> root = cq.from(Employee.class);

            cq.select(root);

            Predicate predicate = null;

            if (!AppUtils.isNullOrEmpty(name)) {
                predicate = cb.or(cb.like(root.get(Employee_.firstName), '%' + name + '%'),
                         cb.like(root.get(Employee_.lastName), '%' + name + '%'));
            }

            if (!AppUtils.isNullOrEmpty(phone)) {

                Join<Employee, Contact> contact = root.join(Employee_.contact);

                Predicate p = cb.or(cb.equal(contact.get(Contact_.phone), phone), cb.equal(contact.get(Contact_.mobilePhone), phone));

                if (predicate == null) {
                    predicate = p;
                } else {
                    predicate = cb.and(predicate, p);
                }
            }

            if (predicate != null) {
                cq.where(predicate);
            }

            TypedQuery<Employee> q = em.createQuery(cq);

            list = q.getResultList();

        } catch (NoResultException ex) {

        }
        return list;
    }

    public boolean isExists(Integer id, String name, Date birthDate) {
        boolean value = false;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Employee> root = cq.from(Employee.class);

            cq.select(cb.count(root));

            Predicate predicate = cb.and(cb.equal(root.get(Employee_.name), name),
                     cb.equal(root.get(Employee_.birthDate),
                            birthDate));

            if (id != null) {
                predicate = cb.and(predicate,
                        cb.notEqual(root.get(Employee_.id), id));
            }

            cq.where(predicate);

            TypedQuery<Long> q = em.createQuery(cq);

            value = q.getSingleResult() != null && q.getSingleResult() > 0;

        } catch (NoResultException ex) {
        }

        return value;
    }

    public List<Employee> list() {
        List<Employee> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

            Root<Employee> root = cq.from(Employee.class);

            cq.select(root);
            cq.orderBy(cb.asc(root.get(Employee_.name)));

            TypedQuery<Employee> q = em.createQuery(cq);

            list = q.getResultList();

        } catch (NoResultException ex) {

        }
        return list;
    }
}
