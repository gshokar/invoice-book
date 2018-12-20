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

        function onToolbarItemClicked(toolbarItem) {

            switch (toolbarItem.typeCode) {
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Find:
                    find();
                    break;
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.New:
                    createNewClient();
                    break;
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Clear:
                    alert("Clear");
                    break;
            }
        }
        ;

        function onTableRowDoubleClicked(client) {
           
            openClient(client);
        }
        ;

        function afterFind(clients, err) {

            component.setResults(clients);
        }

        function find() {

            $aatl_ib.ClientService.find(component.getCriteria(), afterFind);
        }

        function createNewClient() {
            let actionItem = new $aatl_ib.model.gui.ActionItem("Client - New", $aatl_ib.model.gui.ActionItemTypeCode.ClientDetail);
            $aatl_ib.viewController.mainController.openPanel(actionItem);
        }
        ;

        function openClient(client) {

            if (typeof client === 'object') {
                let actionItem = new $aatl_ib.model.gui.ActionItem("Client - " + client.name, $aatl_ib.model.gui.ActionItemTypeCode.ClientDetail);
                actionItem.data = client.number;
                $aatl_ib.viewController.mainController.openPanel(actionItem);
            }
        }

        this.init = function () {
            component.init();
            component.registerOnToolbarItemClicked(onToolbarItemClicked);
            component.registerOnTableRowDoubleClicked(onTableRowDoubleClicked);
        };

        this.getComponent = function () {
            return component;
        };
    }

    return ClientSearchController;
}());