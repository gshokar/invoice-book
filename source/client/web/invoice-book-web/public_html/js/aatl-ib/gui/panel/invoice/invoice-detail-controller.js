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
                case "cancel":
                    component.cancelItemEdit();
                    break;
                case "print":
                    printInvoice();;
                    break;
            }
        }

        function afterInit() {

            component.setTitle(title);

            //$aatl_ib.InvoiceService.get(invoiceNumber, component.setInvoice);

        }
   
        function beforeSave(data) {

            let value = true;

            component.hideError();

            return value;
        }
        function afterSave(invoiceItem, err) {

            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {
                component.afterSaved(invoiceItem);

            }

        }

        function saveData() {

            let timeEntry = component.getEditTimeEntry();

            if (beforeSave(timeEntry)) {
                $aatl_ib.TimeEntryService.save(timeEntry, afterSave);
            }

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
       
       function loadItemTypeDropdownOptions(control){
           $aatl_ib.SalesItemService.itemTypes(function (optionList, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    let options = [];

                    optionList.forEach((option) => {
                        options.push({code: option.number, name: option.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(control, options);
                    
                    component.selectItemType();
                }
            });
       }
        function afterPrint(pdf, err){
            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {
                
                let byteCharacters = window.atob(pdf);
                let byteNumbers = new Array(byteCharacters.length);
                
                for(var i = 0; i < byteCharacters.length; i++){
                    byteNumbers[i] = byteCharacters.charCodeAt(i);
                }

                var byteArray = new Uint8Array(byteNumbers);
                var blob = new Blob([byteArray], {type: "application/pdf"});
                var fileURL = URL.createObjectURL(blob);
                window.open(fileURL, "print-timesheet");
                //window.navigator.msSaveOrOpenBlob(blob, "print-timesheet.pdf");
            }
        }
        
        function printTimeSheet(){
            $aatl_ib.TimeSheetService.print(component.getCriteria(), afterPrint);
        }
        
        this.init = function () {
            component.registerLoadClientOptions(loadClientDropdownOptions);
            component.registerOnActionButtonClicked(onActionButtonClicked);
            component.registerLoadItemTypeOptions(loadItemTypeDropdownOptions)
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
