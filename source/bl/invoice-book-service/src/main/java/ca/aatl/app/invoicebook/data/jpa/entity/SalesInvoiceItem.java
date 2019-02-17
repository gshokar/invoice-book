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
import java.util.List;
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

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="salesinvoiceitem")
public class SalesInvoiceItem extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesInvoiceItemId", unique = true, nullable = false)
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "LineNo", nullable = false)
    private Integer lineNumber;
    
    @Column(name = "Description", nullable = false, length = 100)
    private String description;
    
    @Basic(optional = false)
    @Column(name = "Quantity", nullable = false, precision = 19, scale = 2)
    private BigDecimal quantity;
    
    @Basic(optional = false)
    @Column(name = "Rate", nullable = false, precision = 19, scale = 2)
    private BigDecimal rate;
    
    @Basic(optional = false)
    @Column(name = "Amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Basic(optional = false)
    @Column(name = "TaxAmount", nullable = false, precision = 19, scale = 2)
    private BigDecimal taxAmount;
    
    @Basic(optional = false)
    @Column(name = "TotalAmount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;
    
    @JoinColumn(name = "SalesInvoiceId", referencedColumnName = "SalesInvoiceId", nullable = false)
    @ManyToOne(optional = false)
    private SalesInvoice invoice;
    
    @JoinColumn(name = "SalesItemId", referencedColumnName = "SalesItemId", nullable = false)
    @ManyToOne(optional = false)
    private SalesItem salesItem;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "invoiceItem")
    private List<SalesInvoiceItemTax> taxes;
}
