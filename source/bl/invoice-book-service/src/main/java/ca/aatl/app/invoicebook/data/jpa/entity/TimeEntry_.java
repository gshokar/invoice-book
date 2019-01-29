/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-28  GShokar         Created
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

@StaticMetamodel(TimeEntry.class)
public class TimeEntry_ extends BaseEntity_{
    public static volatile SingularAttribute<TimeEntry, Date> date;
    public static volatile SingularAttribute<TimeEntry, Date> startTime;
    public static volatile SingularAttribute<TimeEntry, Employee> employee;
}
