/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-17  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */
@Entity
@Table(name = "salesinvoiceitemtax")
public class SalesInvoiceItemTax extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesInvoiceItemTaxId", unique = true, nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "Amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @JoinColumn(name = "SalesInvoiceItemId", referencedColumnName = "SalesInvoiceItemId", nullable = false)
    @ManyToOne(optional = false)
    private SalesInvoiceItem invoiceItem;

    @JoinColumn(name = "SalesTaxRateId", referencedColumnName = "SalesTaxRateId", nullable = false)
    @ManyToOne(optional = false)
    private SalesTaxRate taxRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public SalesInvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(SalesInvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public SalesTaxRate getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(SalesTaxRate taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalesInvoiceItemTax other = (SalesInvoiceItemTax) obj;

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return Objects.equals(this.getGuid(), other.getGuid());
    }

    public String taxCode() {
        String code = "";

        if (this.getTaxRate() != null && this.getTaxRate().getTax() != null) {
            code = this.getTaxRate().getTax().getCode();
        }
        return code;
    }
}
