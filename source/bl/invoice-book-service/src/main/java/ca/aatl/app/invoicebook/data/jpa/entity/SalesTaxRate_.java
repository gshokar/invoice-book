/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-30  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity_;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author gshokar
 */

@StaticMetamodel(SalesTaxRate.class)
public class SalesTaxRate_ extends BaseEntity_{
    public static SingularAttribute<SalesTaxRate, Integer> id;
    public static SingularAttribute<SalesTaxRate, Date> fromDate;
    public static SingularAttribute<SalesTaxRate, Date> toDate;
    public static SingularAttribute<SalesTaxRate, Country> country;
    public static SingularAttribute<SalesTaxRate, Province> province;
}
