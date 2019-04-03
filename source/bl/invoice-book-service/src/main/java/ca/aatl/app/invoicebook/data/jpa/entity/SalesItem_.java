/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-16  GShokar         Created
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

@StaticMetamodel(SalesItem.class)
public class SalesItem_ extends BaseEntity_{
    public static volatile SingularAttribute<SalesItem, SalesItemType> itemType;
    public static volatile SingularAttribute<SalesItem, String> name;
    public static volatile SingularAttribute<SalesItem, String> code;
    public static volatile SingularAttribute<SalesItem, Integer> id;
}
