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

$aatl_ib.gui.ClientDetailComponent = (function () {
    function ClientDetailComponent(componentId, parentComponent) {

        let component = new $aatl_ib.gui.Component(componentId, parentComponent);

        let toolbar = null;
        let onToolbarItemClicked = null;

        function afterLoad() {
            toolbar = new $aatl_ib.gui.PanelToolbarComponent("clientDetailPanelToolbar", component.getControl);
            toolbar.init();
            toolbar.registerOnClickActionItem(onToolbarItemClicked);
            addToolbarItems();
        }

        function addToolbarItems() {

            toolbar.addNewActionItem("Save", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Save);
            toolbar.addNewActionItem("Close", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Close);
        };

        this.init = function () {
            this.getComponent().load($aatl_ib.viewController.getViewUrl("client-detail"), afterLoad);

        };

        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            onToolbarItemClicked = actionItemClicked;
        };

        this.getComponent = function () {

            return component.getControl();

        };
    }
    
    return ClientDetailComponent;
}());

