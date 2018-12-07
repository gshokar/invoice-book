
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.ProvinceDao;
import ca.aatl.app.invoicebook.data.jpa.entity.Province;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author gshokar
 */

@Stateless
@LocalBean
public class LookupService {

    @EJB
    ProvinceDao provinceDao;
    
    public List<Province> provinces() throws Exception {
        
        return provinceDao.list();
    }
    
}
