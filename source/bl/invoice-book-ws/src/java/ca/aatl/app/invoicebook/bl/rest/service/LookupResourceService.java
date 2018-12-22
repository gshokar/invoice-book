
package ca.aatl.app.invoicebook.bl.rest.service;

import ca.aatl.app.invoicebook.bl.ejb.LookupService;
import ca.aatl.app.invoicebook.data.jpa.entity.Province;
import ca.aatl.app.invoicebook.dto.ProvinceDto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gshokar
 */

@Stateless
@LocalBean
@Path("lookups")
public class LookupResourceService extends ResponseService{

    @EJB
    LookupService lookupService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/provinces")
    public String provinces() {
        
        try {
            List<Province> provinces = lookupService.provinces();
            
            List<ProvinceDto> provinceList = new ArrayList<>();
            
            if(provinces != null && !provinces.isEmpty()){
                
                provinces.forEach( p -> provinceList.add(new ProvinceDto(p.getCode(), p.getName())));
            }
            
            this.setResponseSuccess(provinceList);
            
        } catch (Exception ex) {
            
            setResponseError("System error failed to get the province list - " + ex.getMessage());
            
            Logger.getLogger(LookupResourceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.getResponseJson();
    }
}
