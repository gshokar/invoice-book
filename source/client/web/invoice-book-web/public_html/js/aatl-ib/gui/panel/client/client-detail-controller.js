/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-22  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.ClientDetailController = (function () {
    function ClientDetailController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.ClientDetailComponent(componentId, parentComponent);

        function onToolbarItemClicked(toolbarItem){
            
            switch(toolbarItem.typeCode){
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Save:
                    alert("Save");
                    break;
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Close:
                    alert("Close");
                    break;
            }
        };
        
        this.init = function () {
            component.init();
            component.registerOnToolbarItemClicked(onToolbarItemClicked);
        };
        
        this.getComponent = function(){
            return component;
        };
    }

    return ClientDetailController;
}());
