/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-24  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity_;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author GShokar
 */

@StaticMetamodel(SalesInvoice.class)
public class SalesInvoice_ extends BaseEntity_{
    public static volatile SingularAttribute<SalesInvoice, String> number;
    public static volatile SingularAttribute<SalesInvoice, Date> date;
    public static volatile SingularAttribute<SalesInvoice, Client> client;
}
