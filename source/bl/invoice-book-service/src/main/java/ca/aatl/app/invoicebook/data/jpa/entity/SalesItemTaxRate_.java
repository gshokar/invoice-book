/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Aug-11  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity_;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author GShokar
 */

@StaticMetamodel(SalesItemTaxRate.class)
public class SalesItemTaxRate_ extends BaseEntity_{
    public static SingularAttribute<SalesItemTaxRate, Integer> id;
    public static SingularAttribute<SalesItemTaxRate, SalesItem> item;
    public static SingularAttribute<SalesItemTaxRate, SalesTaxRate> taxRate;
}
