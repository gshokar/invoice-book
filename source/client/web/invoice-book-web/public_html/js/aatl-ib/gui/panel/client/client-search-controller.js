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

        var component = new $aatl_ib.gui.ClientSearchComponent({componentId: componentId, parentComponent: parentComponent});

        function onActionButtonClicked(action) {

            switch (action) {
                case "find":
                    find();
                    break;
                case "open":
                    alert("Open not implemented yet");
                    break;
                case "new":
                    createNewClient();
                    break;
                case "clear":
                    alert("Clear not implemented yet");
                    break;
            }
        }
    
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
            let actionItem = new $aatl_ib.gui.ActionItem({text: "Client - New", typeCode: $aatl_ib.gui.ActionItemTypeCode.ClientDetail});
            $aatl_ib.viewController.mainController.openPanel(actionItem);
        }
        ;

        function openClient(client) {

            if (typeof client === 'object') {
                let actionItem = new $aatl_ib.gui.ActionItem({text: "Client - " + client.name, typeCode: $aatl_ib.gui.ActionItemTypeCode.ClientDetail});
                actionItem.data = client.number;
                $aatl_ib.viewController.mainController.openPanel(actionItem);
            }
        }

        this.init = function () {
            component.init();
            component.registerOnActionButtonClicked(onActionButtonClicked);
            component.registerOnTableRowDoubleClicked(onTableRowDoubleClicked);
        };

        this.getComponent = function () {
            return component;
        };
    }

    return ClientSearchController;
}());