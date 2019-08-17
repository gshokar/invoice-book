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

import ca.aatl.app.invoicebook.data.SalesInvoiceStatusEnum;
import ca.aatl.app.invoicebook.data.jpa.dao.SalesInvoiceDao;
import ca.aatl.app.invoicebook.data.jpa.entity.ClientAddress;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoice;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItem;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesInvoiceItemTax;
import ca.aatl.app.invoicebook.data.jpa.entity.SalesTaxRate;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class SalesInvoiceService {

    @EJB
    SalesInvoiceDao dao;

    @EJB
    SalesItemTaxRateService salesItemTaxRateService;

    @EJB
    SequenceService sequenceService;

    public SalesInvoice newEntity() {
        return new SalesInvoice();
    }

    public SalesInvoice find(String number) throws Exception {
        return dao.find(number);
    }

    public void save(SalesInvoice entity) throws Exception {
        beforeSave(entity);
        dao.save(entity);
    }

    private void beforeSave(SalesInvoice entity) throws Exception {
        validate(entity);

        if (entity.getId() == null) {

            entity.setAddedBy(entity.getLastUpdatedBy());
            entity.setAddedDate(entity.getLastUpdatedDate());
            entity.getGuid();
            entity.getPaidAmount();

            synchronized (this) {
                entity.setNumber(sequenceService.next("InvoiceNumber"));
            }

            entity.setStatus(dao.invoiceStatus(SalesInvoiceStatusEnum.New.toString()));
        }

        for (SalesInvoiceItem sii : entity.getItems()) {

            if (sii != null) {
                if (sii.getInvoice() == null) {
                    sii.setInvoice(entity);
                }

                if (sii.getId() == null) {

                    sii.setAddedBy(entity.getLastUpdatedBy());
                    sii.setAddedDate(entity.getLastUpdatedDate());
                    sii.getGuid();

                }

                sii.setLastUpdatedBy(entity.getLastUpdatedBy());
                sii.setLastUpdatedDate(entity.getLastUpdatedDate());

                if (sii.getTaxes() != null && !sii.getTaxes().isEmpty()) {

                    for (SalesInvoiceItemTax it : sii.getTaxes()) {
                        if (it != null) {
                            if (it.getInvoiceItem() == null) {
                                it.setInvoiceItem(sii);
                            }

                            if (it.getId() == null) {

                                it.setAddedBy(entity.getLastUpdatedBy());
                                it.setAddedDate(entity.getLastUpdatedDate());
                                it.getGuid();

                            }

                            it.setLastUpdatedBy(entity.getLastUpdatedBy());
                            it.setLastUpdatedDate(entity.getLastUpdatedDate());
                        }
                    }
                }
            }
        }

        // This is here because getting presistence error
        updateAmounts(entity);

        for (SalesInvoiceItem sii : entity.getItems()) {

            if (sii != null) {

                for (SalesInvoiceItemTax siit : sii.getTaxes()) {
                    if (siit.getId() == null) {

                        siit.setAddedBy(entity.getLastUpdatedBy());
                        siit.setAddedDate(entity.getLastUpdatedDate());
                        siit.getGuid();

                    }

                    siit.setLastUpdatedBy(entity.getLastUpdatedBy());
                    siit.setLastUpdatedDate(entity.getLastUpdatedDate());

                    if (siit.getInvoiceItem() == null) {
                        siit.setInvoiceItem(sii);
                    }
                }
            }
        }
    }

    private void validate(SalesInvoice entity) throws DataValidationException {
        boolean isValid = true;

        StringBuilder sb = new StringBuilder();

        if (entity != null) {

            if (entity.getDate() == null) {
                isValid = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the invoice date.");
            } else if (entity.getDate().before(AppUtils.getMinimumDate())) {
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
                if (it.getSalesItem() == null) {
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
        if (!isValid) {
            throw new DataValidationException(sb.toString());
        }
    }

    private void updateAmounts(SalesInvoice entity) throws Exception {
        entity.setAmount(BigDecimal.ZERO);
        entity.setTaxAmount(BigDecimal.ZERO);

        for (SalesInvoiceItem sit : entity.getItems()) {

            sit.setAmount(sit.getQuantity().multiply(sit.getRate()).setScale(2, RoundingMode.HALF_UP));
            updateTaxAmount(sit);
            sit.setTotalAmount(sit.getAmount().add(sit.getTaxAmount()));

            entity.setAmount(entity.getAmount().add(sit.getAmount()));
            entity.setTaxAmount(entity.getTaxAmount().add(sit.getTaxAmount()));
        }

        entity.setTotalAmount(entity.getAmount().add(entity.getTaxAmount()));
    }

    private void updateTaxAmount(SalesInvoiceItem sit) throws Exception {
        sit.setTaxAmount(BigDecimal.ZERO);

        if (sit.getTaxes() == null) {
            sit.setTaxes(new ArrayList<>());
        }

        ClientAddress address = sit.getInvoice().getClient().primaryAddress();
        Integer countryId = null;
        Integer provinceId = null;

        if (address.getAddress().getCountry() != null) {
            countryId = address.getAddress().getCountry().getId();
        }

        if (address.getAddress().getProvince() != null) {
            provinceId = address.getAddress().getProvince().getId();
        }

        List<SalesTaxRate> taxRates = salesItemTaxRateService.list(sit.getSalesItem().getId(), sit.getInvoice().getDate(), countryId, provinceId);

        if (taxRates != null && !taxRates.isEmpty()) {

            //Remove the tax items no more applied
            for (int i = sit.getTaxes().size() - 1; i >= 0; i--) {

                if (!isSalesItemTaxRateContainsInList(taxRates, sit.getTaxes().get(i).getTaxRate())) {

                    if (sit.getTaxes().get(i).getId() != null && sit.getTaxes().get(i).getId() > 0) {
                        salesItemTaxRateService.delete(sit.getTaxes().get(i));
                    }
                    sit.getTaxes().remove(i);
                }
            }

            // Add the tax items not in list
            for (SalesTaxRate sitr : taxRates) {

                if (!isSalesItemTaxRateContains(sit.getTaxes(), sitr)) {
                    sit.getTaxes().add(createSalesInvoiceItemTax(sit, sitr));
                }
            }

            for (SalesInvoiceItemTax it : sit.getTaxes()) {
                sit.setTaxAmount(sit.getTaxAmount().add(it.getAmount()));
            }

        } else if (sit.getTaxes() != null && !sit.getTaxes().isEmpty()) {
            //Remove the tax items no more applied
            for (int i = sit.getTaxes().size() - 1; i >= 0; i--) {

                if (sit.getTaxes().get(i).getId() != null && sit.getTaxes().get(i).getId() > 0) {
                    salesItemTaxRateService.delete(sit.getTaxes().get(i));
                }
                sit.getTaxes().remove(i);

            }
        }

    }

    private boolean isSalesItemTaxRateContainsInList(List<SalesTaxRate> taxRates, SalesTaxRate taxRate) {
        return taxRates.stream().anyMatch(tr -> tr.equals(taxRate));
    }

    private boolean isSalesItemTaxRateContains(List<SalesInvoiceItemTax> taxes, SalesTaxRate sitr) {
        boolean value = false;

        if (taxes != null && !taxes.isEmpty()) {
            value = taxes.stream().anyMatch(tr -> tr.getTaxRate().equals(sitr));
        }
        return value;
    }

    private SalesInvoiceItemTax createSalesInvoiceItemTax(SalesInvoiceItem sit, SalesTaxRate sitr) {
        SalesInvoiceItemTax itemTax = new SalesInvoiceItemTax();

        itemTax.setInvoiceItem(sit);
        itemTax.setTaxRate(sitr);
        itemTax.setAmount(sit.getAmount().multiply(sitr.getRate()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN));

        return itemTax;
    }

    public List<SalesInvoice> find(String number, Date date, String clientNumber) throws Exception {
        return dao.find(number, date, clientNumber);
    }

}
