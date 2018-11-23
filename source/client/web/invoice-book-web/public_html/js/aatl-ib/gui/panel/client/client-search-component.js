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

$aatl_ib.gui.ClientSearchComponent = (function () {
    function ClientSearchComponent(componentId, parentComponent) {

        let component = new $aatl_ib.gui.Component(componentId, parentComponent);

        let toolbar = null;
        let onToolbarItemClicked = null;
        
        function afterLoad(){
            toolbar = new $aatl_ib.gui.PanelToolbarComponent("clientSearchPanelToolbar", component.getControl);
            toolbar.init();
            toolbar.registerOnClickActionItem(onToolbarItemClicked);
            addToolbarItems();
        }
        
        function addToolbarItems(){
            
            toolbar.addNewActionItem("Find", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Find);
            toolbar.addNewActionItem("Open", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Open);
            toolbar.addNewActionItem("New", $aatl_ib.model.gui.PanelToolbarItemTypeCode.New);
            toolbar.addNewActionItem("Clear", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Clear);
        };
        
        this.init = function () {
            this.getComponent().load($aatl_ib.viewController.getViewUrl("client-search"), afterLoad);
            
        };

        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            onToolbarItemClicked = actionItemClicked;
        };

        this.getComponent = function () {
            
            return component.getControl();
            
        };
      
    }

    return ClientSearchComponent;
}());
