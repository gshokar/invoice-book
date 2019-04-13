/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-21  GShokar         Created
 * =============================================================================
 */
"use strict";

$aatl_ib.gui.InvoiceDetailController = (function () {
    function InvoiceDetailController(componentId, parentComponent) {

        let invoiceNumber = "";
        let title = "";

        var component = new $aatl_ib.gui.InvoiceDetailComponent({componentId: componentId, parentComponent: parentComponent});

        function onActionButtonClicked(action) {

            switch (action) {
                case "save":
                    saveData();
                    break;
                case "addItem":
                    component.addItem();
                    break;
                case "editItem":
                    component.editItem();
                    break;
                case "updateItem":
                    component.updateItem();
                    break;
                case "cancel":
                    component.cancelItemEdit();
                    break;
                case "print":
                    printInvoice();
                    ;
                    break;
            }
        }

        function afterInit() {

            component.setTitle(title);

            $aatl_ib.InvoiceService.get(invoiceNumber, component.setInvoice);

        }

        function beforeSave(data) {

            let value = true;

            component.hideError();

            return value;
        }
        function afterSave(invoice, err) {

            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {
                component.setInvoice(invoice);
                
                let currentTitle = title;
                
                title = 'Invoice - ' + invoice.number; 
                
                component.setTitle(title);
                
                $aatl_ib.viewController.mainController.updateActionItemText(currentTitle, title);
            }
        }

        function saveData() {

            let invoice = component.getEditInvoice();

            if (beforeSave(invoice)) {
                $aatl_ib.InvoiceService.save(invoice, afterSave);
            }

        }

        function loadClientDropdownOptions(clientControl) {

            $aatl_ib.ClientService.list(function (clients, err) {

                if (err !== undefined && err !== null) {
                    component.showError(err);
                } else {
                    let options = [{code: "", name: ""}];

                    clients.forEach((client) => {
                        options.push({code: client.number, name: client.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(clientControl, options);
                    
                    component.selectClient();
                }
            });
        }

        function loadItemTypeDropdownOptions(control) {
            $aatl_ib.SalesItemService.itemTypes(function (optionList, err) {

                if (err !== undefined && err !== null) {
                    component.showError(err);
                } else {
                    let options = [];

                    optionList.forEach((option) => {
                        options.push({code: option.name, name: option.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(control, options);

                    component.selectItemType();
                }
            });
        }

        function loadItemDropdownOptions(control, itemType) {
            $aatl_ib.SalesItemService.itemsByItemType(itemType, function (optionList, err) {

                if (err !== undefined && err !== null) {
                    component.showError(err);
                } else {
                    let options = [{code: "", name: ""}];

                    optionList.forEach((option) => {
                        options.push({code: option.code, name: option.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(control, options);

                    component.setSalesItems(optionList);
                    component.selectItem();
                }
            });
        }
        function afterPrint(pdf, err) {
            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {

                let byteCharacters = window.atob(pdf);
                let byteNumbers = new Array(byteCharacters.length);

                for (var i = 0; i < byteCharacters.length; i++) {
                    byteNumbers[i] = byteCharacters.charCodeAt(i);
                }

                var byteArray = new Uint8Array(byteNumbers);
                var blob = new Blob([byteArray], {type: "application/pdf"});
                var fileURL = URL.createObjectURL(blob);
                window.open(fileURL, "print-timesheet");
                //window.navigator.msSaveOrOpenBlob(blob, "print-timesheet.pdf");
            }
        }

        function printTimeSheet() {
            $aatl_ib.TimeSheetService.print(component.getCriteria(), afterPrint);
        }

        function onValueChanged(evt) {

            let $element = $(evt.target);

            if (component.isItemTypeControl($element)) {

                let itemControl = component.getItemControl();

                loadItemDropdownOptions(itemControl, $element.val());

            } else if (component.isItemControl($element)) {
                component.itemChanged($element.val());
            } else if (component.isCalcAmount($element)) {
                component.calcAmount();
            }

            // set save enabled
            component.setSaveEnabled(true);
        }
        this.init = function () {
            component.registerLoadClientOptions(loadClientDropdownOptions);
            component.registerOnActionButtonClicked(onActionButtonClicked);
            component.registerLoadItemTypeOptions(loadItemTypeDropdownOptions);
            component.registerOnFieldValueChanged(onValueChanged);
            component.setAfterInit(afterInit);
            component.init();
        };

        this.getComponent = function () {
            return component;
        };

        this.setTitle = function (value) {
            title = value;
        };

        this.setInvoiceNumber = function (number) {
            invoiceNumber = number;
        };
    }

    return InvoiceDetailController;
}());
