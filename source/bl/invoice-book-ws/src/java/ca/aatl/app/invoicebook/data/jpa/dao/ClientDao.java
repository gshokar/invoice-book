/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-08  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.Client;
import ca.aatl.app.invoicebook.data.jpa.entity.Client_;
import ca.aatl.app.invoicebook.data.jpa.entity.Contact;
import ca.aatl.app.invoicebook.data.jpa.entity.Contact_;
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
public class ClientDao extends AbstractDao<Client> {

    @Override
    public void save(Client entity) throws Exception {
        if (entity != null) {

            if (entity.getId() == null) {

                create(entity);
            } else {
                update(entity);
            }
        }
    }

    @Override
    public void beforeCreate(Client entity) throws Exception {
        
        if(entity != null){
            
//            if(entity.hasAddress()){
//                
//                entity.getAddresses().forEach(ca -> {
//                    
//                    if(ca.getAddress() != null && ca.getAddress().getId() ==  null){
//                        ca.setClient(entity);
//                        //em.persist(ca.getAddress());
//                    }
//                });
//            }
            
            if(entity.hasContact()){
                entity.getContacts().forEach(cc -> {
                    
                    if(cc.getContact() != null && cc.getContact().getId() ==  null){
                        //cc.setClient(entity);
                        em.persist(cc.getContact());
                    }
                });
            }
        }
    }

    @Override
    public void beforeUpdate(Client entity) throws Exception {

    }

    public Client find(String number) {
        Client client = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Client> cq = cb.createQuery(Client.class);

            Root<Client> root = cq.from(Client.class);

            cq.select(root);
            cq.where(cb.equal(root.get(Client_.number), number));

            TypedQuery<Client> q = em.createQuery(cq);

            client = q.getSingleResult();

            if (client != null) {
                client.getAddresses().size();
                client.getContacts().size();
            }

        } catch (NoResultException ex) {

        }
        return client;
    }

    public List<Client> find(String name, String phone) {
        List<Client> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Client> cq = cb.createQuery(Client.class);

            Root<Client> root = cq.from(Client.class);

            cq.select(root);

            Predicate predicate = null;

            if (!AppUtils.isNullOrEmpty(name)) {
                predicate = cb.like(root.get(Client_.name), '%' + name + '%');
            }

            if (!AppUtils.isNullOrEmpty(phone)) {

                Join<Client, Contact> contact = root.join(Client_.contacts);

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

            TypedQuery<Client> q = em.createQuery(cq);

            list = q.getResultList();

            if (list != null && !list.isEmpty()) {
                list.forEach(c -> {
                    c.getAddresses().size();
                    c.getContacts().size();
                });
            }
        } catch (NoResultException ex) {

        }
        return list;
    }

    public boolean isExists(Integer id, String number, String name) {
        boolean value = false;
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Client> root = cq.from(Client.class);

            cq.select(cb.count(root));

            Predicate predicate = cb.equal(root.get(Client_.name),
                    name);

            if (id != null) {
                predicate = cb.and(predicate,
                        cb.notEqual(root.get(Client_.id), id));
            }

            if (number != null) {
                predicate = cb.and(predicate,
                        cb.notEqual(root.get(Client_.number), number));
            }
            
            cq.where(predicate);

            TypedQuery<Long> q = em.createQuery(cq);

            value = q.getSingleResult() != null && q.getSingleResult() > 0;

        } catch (NoResultException ex) {
        }
        
        return value;
    }

}
