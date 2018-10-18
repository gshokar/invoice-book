/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-26  GShokar         Created
 * =============================================================================
 */


"use strict";

$aatl_ib.model.gui.ActionItem = (function(){
    
    function ActionItem(text, id, typeCode, data){
        this.text = text;
        this.id = id;
        this.typeCode = typeCode;
        this.controlId = "#" + id;
        this.viewComponent = undefined;
        this.data = data;
    }
    
    return ActionItem;
    
}());

$aatl_ib.model.gui.ActionItemTypeCode = {

    Home: "home",
    ClientSearch: "clientSearch",
    Save:'save',
    Open: 'open',
    Close: 'close',
    New: 'new',
    Reset: 'reset'
};