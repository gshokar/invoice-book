/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-19  GShokar         Created
 * =============================================================================
 */


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

$aatl_ib.gui.InvoiceSearchController = (function () {
    function InvoiceSearchController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.InvoiceSearchComponent({componentId: componentId, parentComponent: parentComponent});

        function onActionButtonClicked(action) {

            switch (action) {
                case "find":
                    find();
                    break;
                case "open":
                    alert("Open not implemented yet");
                    break;
                case "new":
                    createNewInvoice();
                    break;
                case "clear":
                    alert("Clear not implemented yet");
                    break;
            }
        }
    
        function onTableRowDoubleClicked(client) {

            openInvoice(client);
        }
        
        function loadClientDropdownOptions(clientControl) {

            $aatl_ib.ClientService.list(function (clients, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    let options = [{code: "", name: ""}];

                    clients.forEach((client) => {
                        options.push({code: client.number, name: client.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(clientControl, options);

                }
            });
        }

        function afterFind(clients, err) {

            component.setResults(clients);
        }

        function find() {

            $aatl_ib.ClientService.find(component.getCriteria(), afterFind);
        }

        function createNewInvoice() {
            let actionItem = new $aatl_ib.gui.ActionItem({text: "Invoice - New", typeCode: $aatl_ib.gui.ActionItemTypeCode.InvoiceDetail});
            $aatl_ib.viewController.mainController.openPanel(actionItem);
        }
        ;

        function openInvoice(invoice) {

            if (typeof invoice === 'object') {
                let actionItem = new $aatl_ib.gui.ActionItem({text: "Invoice - " + invoice.number, typeCode: $aatl_ib.gui.ActionItemTypeCode.InvoiceDetail});
                actionItem.data = invoice.number;
                $aatl_ib.viewController.mainController.openPanel(actionItem);
            }
        }

        this.init = function () {
            component.registerOnActionButtonClicked(onActionButtonClicked);
            component.registerOnTableRowDoubleClicked(onTableRowDoubleClicked);
            component.registerLoadClientOptions(loadClientDropdownOptions);
            component.init();
        };

        this.getComponent = function () {
            return component;
        };
    }

    return InvoiceSearchController;
}());