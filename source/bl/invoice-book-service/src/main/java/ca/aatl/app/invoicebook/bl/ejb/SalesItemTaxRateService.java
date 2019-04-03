/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-26  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.SalesItemTaxRateDao;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemTaxRate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author GShokar
 */

@Stateless
@LocalBean
public class SalesItemTaxRateService {

    @EJB
    SalesItemTaxRateDao dao;
    
    public List<SalesItemTaxRate> list(Integer salesItemId, Date date, Integer countryId, Integer provinceId) {
        List<SalesItemTaxRate> taxRates = new ArrayList<>();
        
        List<SalesItemTaxRate> list = dao.list(salesItemId, date, countryId, provinceId);
                
        if(list != null && !list.isEmpty()){
            
            //Sort the tax rates by sales tax and descending from date
            list.sort((SalesItemTaxRate t1, SalesItemTaxRate t2) -> {
                // Compare sales tax
                int value = t1.getTax().getId().compareTo(t2.getTax().getId());
                
                if(value == 0){
                    value = t2.getFromDate().compareTo(t1.getFromDate());
                }
                return value;
            });
            
            SalesItemTaxRate currentTaxRate = null;
            
            for(SalesItemTaxRate sitr : list){
                
                if(currentTaxRate == null){
                    currentTaxRate = sitr;
                }else if(!currentTaxRate.equals(sitr)){
                    taxRates.add(currentTaxRate);
                    currentTaxRate = sitr;
                }
            }
            
            if(currentTaxRate != null){
                taxRates.add(currentTaxRate);
            }
        }
        return taxRates;
    }
    
}
