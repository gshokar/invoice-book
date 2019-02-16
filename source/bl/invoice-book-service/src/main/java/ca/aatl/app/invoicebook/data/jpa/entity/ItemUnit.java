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

import ca.aatl.app.invoicebook.data.jpa.entity.base.TypeEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="itemunit")
public class ItemUnit extends TypeEntity{
    
}
