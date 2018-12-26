/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-25  GShokar         Created
 * =============================================================================
 */


"use strict";

$aatl_ib.model.gui.Panel = (function(){
    
    function Panel(typeCode, controlId){
        this.typeCode = typeCode;
        this.title = undefined;
        this.controlId = controlId;
        this.controller = undefined;
        this.linkedActionItem = undefined;

        
        this.setLinkedActionItem = function(actionItem){
            this.linkedActionItem = actionItem;
        };
    
    }

    
    return Panel;
    
}());

$aatl_ib.model.gui.PanelTypeCode = {
  
    Home: "home",
    ClientSearch: "clientSearch",
    Client: "client",
    EmployeeSearch: "employeeSearch",
    Employee: "employee"
};