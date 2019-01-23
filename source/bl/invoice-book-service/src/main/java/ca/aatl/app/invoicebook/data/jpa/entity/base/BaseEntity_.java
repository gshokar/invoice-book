/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-31  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author GShokar
 */

@StaticMetamodel(BaseEntity.class)
public class BaseEntity_ {
    
    public static volatile SingularAttribute<BaseEntity, String> guid;
    public static volatile SingularAttribute<BaseEntity, Date> addedDate;
    public static volatile SingularAttribute<BaseEntity, Integer> lastUpdatedBy;
    public static volatile SingularAttribute<BaseEntity, Date> lastUpdatedDate;
    public static volatile SingularAttribute<BaseEntity, Integer> addedBy;
    
}
