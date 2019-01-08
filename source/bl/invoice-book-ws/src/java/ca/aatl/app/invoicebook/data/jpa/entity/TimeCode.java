/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-26  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.TypeEntity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="timecode")
public class TimeCode extends TypeEntity{
    
    @Basic(optional = false)
    @Column(name = "Active", nullable=false)
    private boolean active;
    
    @Basic(optional = false)
    @Column(name = "Chargeable", nullable=false)
    private boolean chargeable;
    
    @Basic(optional = false)
    @Column(name = "Code", nullable=false, length = 10)
    private String code;
    
    @JoinColumn(name = "ClientId", referencedColumnName = "EntityId")
    @ManyToOne(optional = true)
    private Client client;
    
    @JoinColumn(name = "ClientLocationId", referencedColumnName = "EntityId")
    @ManyToOne(optional = true)
    private ClientLocation clientLocation;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isChargeable() {
        return chargeable;
    }

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientLocation getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(ClientLocation clientLocation) {
        this.clientLocation = clientLocation;
    }
    
}
