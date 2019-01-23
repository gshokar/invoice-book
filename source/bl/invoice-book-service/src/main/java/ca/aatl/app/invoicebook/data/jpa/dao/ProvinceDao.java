
package ca.aatl.app.invoicebook.data.jpa.dao;

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
public class ProvinceDao extends BaseDao{

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

    public Province findByCode(String code) {
        Province province = null;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Province> cq = cb.createQuery(Province.class);

            Root<Province> root = cq.from(Province.class);

            cq.select(root);
            cq.where(cb.equal(root.get(Province_.code), code));

            TypedQuery<Province> q = em.createQuery(cq);

            province =  q.getSingleResult();
            
        } catch (NoResultException ex) {

        }
        
        return province;
    }
    
}
