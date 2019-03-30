/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-23  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.SalesInvoiceDao;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientAddress;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoice;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesItemTaxRate;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
public class SalesInvoiceService {
    
    @EJB
    SalesInvoiceDao dao;
    
    @EJB
    SalesItemTaxRateService salesItemTaxRateService;
    
    public SalesInvoice newEntity() {
        return new SalesInvoice();
    }

    public SalesInvoice find(String number) throws Exception{
        return dao.find(number);
    }

    public void save(SalesInvoice entity) throws Exception{
        beforeSave(entity);
    }

    private void beforeSave(SalesInvoice entity) throws DataValidationException {
        validate(entity);
        
        if (entity.getId() == null) {

            entity.setAddedBy(entity.getLastUpdatedBy());
            entity.setAddedDate(entity.getLastUpdatedDate());
            entity.getGuid();
            
        }
        
        for(SalesInvoiceItem sit : entity.getItems()){
            
            if(sit != null){
                if(sit.getInvoice() == null){
                    sit.setInvoice(entity);
                }
            }
        }
        updateAmounts(entity);
    }

    private void validate(SalesInvoice entity) throws DataValidationException {
        boolean isValid = true;

        StringBuilder sb = new StringBuilder();
        
        if(entity != null){
            
        if (entity.getDate() == null) {
            isValid = false;
            sb.append(System.lineSeparator());
            sb.append("Enter the invoice date.");
        }else if(entity.getDate().before(AppUtils.getMinimumDate())){
            isValid = false;
            sb.append(System.lineSeparator());
            sb.append("Enter the valid invoice date, date can not be before 1900-01-01.");
        }

        if (entity.getClient() == null) {
            isValid = false;
            sb.append(System.lineSeparator());
            sb.append("Select the client.");
        }

        if (entity.getItems().size() == 0) {
            isValid = false;
            sb.append(System.lineSeparator());
            sb.append("Add the item to invoice before save.");
        }

        for (SalesInvoiceItem it : entity.getItems()) {
            if (it.getSalesItem() == null ) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Select the invoice item.");
            }

            if (AppUtils.isNullOrEmpty(it.getDescription())) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the item description.");
            }

            if (it.getQuantity().doubleValue() <= 0) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the valid item quantity.");
            }

            if (it.getRate().doubleValue() <= 0) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the valid item rate.");
            }
        }
        }
        if(!isValid){
            throw new DataValidationException(sb.toString());
        }
    }

    private void updateAmounts(SalesInvoice entity) {
        entity.setAmount(BigDecimal.ZERO);
        entity.setTaxAmount(BigDecimal.ZERO);
        //entity.setTotalAmount(BigDecimal.ZERO);
        
        for(SalesInvoiceItem sit : entity.getItems()){
            
            sit.setAmount(sit.getQuantity().multiply(sit.getRate()).setScale(2, RoundingMode.HALF_UP));
            updateTaxAmount(sit);
            
            entity.setAmount(entity.getAmount().add(sit.getAmount()));
        }
    }

    private void updateTaxAmount(SalesInvoiceItem sit) {
        sit.setTaxAmount(BigDecimal.ZERO);
        
        if(sit.getTaxes() == null){
              sit.setTaxes(new ArrayList<>());
        }
        
        ClientAddress address = sit.getInvoice().getClient().primaryAddress();
        Integer countryId = null;
        Integer provinceId = null;
        
        if(address.getAddress().getCountry() != null){
            countryId = address.getAddress().getCountry().getId();
        }
        
        if(address.getAddress().getProvince() != null){
           provinceId = address.getAddress().getProvince().getId();
        }
        
        List<SalesItemTaxRate> taxRates = salesItemTaxRateService.list(sit.getId(), sit.getInvoice().getDate(), countryId, provinceId);
    }
    
}
