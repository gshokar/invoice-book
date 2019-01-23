/*
 * Copyright (c) 2014 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2014-Aug-07  GShokar         Created
 * =============================================================================
 */

package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.AddressEntity_;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author GShokar
 */
@StaticMetamodel(Address.class)
public class Address_ extends AddressEntity_{ 

//    public static volatile ListAttribute<Address, InstitutionAddress> institutionAddress;
//    public static volatile ListAttribute<Address, AgentAddress> agentAddresses;
    public static volatile SingularAttribute<Address, Province> province;
    public static volatile SingularAttribute<Address, Country> country;
    
}
