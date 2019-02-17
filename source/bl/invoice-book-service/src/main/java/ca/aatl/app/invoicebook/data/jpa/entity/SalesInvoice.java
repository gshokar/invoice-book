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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="salesinvoice")
public class SalesInvoice extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesInvoiceId", unique = true, nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "InvoiceDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "InvoiceNo", nullable = true, length = 10)
    private String number;
    
    @Basic(optional = false)
    @Column(name = "Amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Basic(optional = false)
    @Column(name = "TaxAmount", nullable = false, precision = 19, scale = 2)
    private BigDecimal taxAmount;
    
    @Basic(optional = false)
    @Column(name = "TotalAmount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;
    
    @Basic(optional = false)
    @Column(name = "PaidAmount", nullable = false, precision = 19, scale = 2)
    private BigDecimal paidAmount;
    
    @JoinColumn(name = "InvoiceStatusId", referencedColumnName = "TypeId", nullable = false)
    @ManyToOne(optional = false)
    private SalesInvoiceStatus status;
    
    @JoinColumn(name = "CompanyId", referencedColumnName = "EntityId", nullable = false)
    @ManyToOne(optional = false)
    private Company company;
    
    @JoinColumn(name = "ClientId", referencedColumnName = "EntityId", nullable = false)
    @ManyToOne(optional = false)
    private Client client;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "invoice")
    private List<SalesInvoiceItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public SalesInvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(SalesInvoiceStatus status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<SalesInvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<SalesInvoiceItem> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final SalesInvoice other = (SalesInvoice) obj;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return Objects.equals(this.getGuid(), other.getGuid());
    }
}
