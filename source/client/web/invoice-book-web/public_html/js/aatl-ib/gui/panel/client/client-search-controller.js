/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-21  GShokar         Created
 * =============================================================================
 */


"use strict";

$aatl_ib.gui.ClientSearchController = (function () {
    function ClientSearchController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.ClientSearchComponent(componentId, parentComponent);

        function onToolbarItemClicked(toolbarItem){
            
            switch(toolbarItem.typeCode){
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Find:
                    alert("Find");
                    break;
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.New:
                    alert("New");
                    break;
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Clear:
                    alert("Clear");
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

    return ClientSearchController;
}());