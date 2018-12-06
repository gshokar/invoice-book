
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.LookupService;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author gshokar
 */

@Stateless
@LocalBean
public class LookupResponseService extends ResponseService{

    @EJB
    LookupService lookupService;
    
    @Override
    public void processRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
