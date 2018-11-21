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

        let id = componentId;
        let controlId = "#" + id;
        let parent = parentComponent;

        let toolbar = new $aatl_ib.gui.PanelToolbarComponent("clientSearchPanelToolbar");

        function afterLoad(){
            toolbar.init();
            addToolbarItems();
        }
        
        function addToolbarItems(){
            
            toolbar.addNewActionItem("Find", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Find);
            toolbar.addNewActionItem("New", $aatl_ib.model.gui.PanelToolbarItemTypeCode.New);
            toolbar.addNewActionItem("Clear", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Clear);
        };
        
        this.init = function () {
            this.getComponent().load($aatl_ib.viewController.getViewUrl("clientSearch"), afterLoad);
            
        };

        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            toolbar.registerOnClickActionItem(actionItemClicked);
        };

        this.getComponent = function () {

            if (parent && typeof parent === 'function') {
                return parent().find(controlId);
            }

            return $(controlId);
        };

        this.getId = function () {
            return id;
        };
    }

    return ClientSearchComponent;
}());
