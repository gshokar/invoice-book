
package ca.aatl.app.invoicebook.data.jpa.dao;

import ca.aatl.app.invoicebook.data.jpa.entity.AppUser;
import ca.aatl.app.invoicebook.data.jpa.entity.AppUser_;
import ca.aatl.app.invoicebook.data.jpa.entity.Province;
import ca.aatl.app.invoicebook.data.jpa.entity.Province_;
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
public class ProvinceDao extends AbstractDao<Province>{

    @Override
    public void save(Province entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void beforeCreate(Province entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void beforeUpdate(Province entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Province> list() throws Exception{
        
        List<Province> list = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Province> cq = cb.createQuery(Province.class);

            Root<Province> root = cq.from(Province.class);

            cq.select(root);
            cq.orderBy(cb.asc(root.get(Province_.name)));

            TypedQuery<Province> q = em.createQuery(cq);

            list =  q.getResultList();
            
        } catch (NoResultException ex) {

        }
        
        return list;
    }
    
}
