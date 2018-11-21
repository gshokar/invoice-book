/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-11  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.model.gui.PanelToolbarItem = (function(){
    
    function PanelToolbarItem(text, id, typeCode, data){
        this.text = text;
        this.id = id;
        this.typeCode = typeCode;
        this.controlId = "#" + id;
        this.viewComponent = undefined;
        this.data = data;
    }
    
    return PanelToolbarItem;
    
}());

$aatl_ib.model.gui.PanelToolbarItemTypeCode = {
   
    Save:'save',
    Open: 'open',
    Close: 'close',
    New: 'new',
    Reset: 'reset',
    Find: 'find',
    Clear: 'clear'
};