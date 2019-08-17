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
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItemTax;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemTaxRate;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesTaxRate;
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
    
    public List<SalesTaxRate> list(Integer salesItemId, Date date, Integer countryId, Integer provinceId) throws Exception{
        List<SalesTaxRate> taxRates = new ArrayList<>();
        
        List<SalesItemTaxRate> list = dao.list(salesItemId, date, countryId, provinceId);
                
        if(list != null && !list.isEmpty()){
            
            //Sort the tax rates by sales tax and descending from date
            list.sort((SalesItemTaxRate t1, SalesItemTaxRate t2) -> {
                // Compare sales tax
                int value = t1.getTaxRate().getTax().getId().compareTo(t2.getTaxRate().getTax().getId());
                
                if(value == 0){
                    value = t2.getTaxRate().getFromDate().compareTo(t1.getTaxRate().getFromDate());
                }
                return value;
            });
            
            SalesTaxRate currentTaxRate = null;
            
            for(SalesItemTaxRate sitr : list){
                
                if(currentTaxRate == null){
                    currentTaxRate = sitr.getTaxRate();
                }else if(!currentTaxRate.equals(sitr)){
                    taxRates.add(currentTaxRate);
                    currentTaxRate = sitr.getTaxRate();
                }
            }
            
            if(currentTaxRate != null){
                taxRates.add(currentTaxRate);
            }
        }
        return taxRates;
    }

    public void delete(SalesInvoiceItemTax entity) throws Exception{
        dao.delete(entity);
    }
    
}
