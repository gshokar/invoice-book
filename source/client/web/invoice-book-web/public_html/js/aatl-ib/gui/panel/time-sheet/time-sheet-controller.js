/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-25  GShokar         Created
 * =============================================================================
 */
"use strict";

$aatl_ib.gui.TimeSheetController = (function () {
    function TimeSheetController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.TimeSheetComponent({componentId: componentId, parentComponent: parentComponent});

        function onActionButtonClicked(action) {

            switch (action) {
                case "save":
                    saveData();
                    break;
                case "add":
                    component.addTimeEntry();
                    break;
                case "cancel":
                    component.cancelTimeEntryEdit();
                    break;
                case "print":
                    printTimeSheet();;
                    break;
            }
        }

        function afterInit() {
//            $aatl_ib.TimeEntryService.list(function (list, err) {
//                if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
//                    component.showError(err);
//                } else {
//                    component.setTimeEntries(list);
//                }
//            });
        }

        function crteriaChanged(criteria) {
            $aatl_ib.TimeEntryService.find(criteria, function (list, err) {
                if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                    component.showError(err);
                } else {
                    component.setTimeEntries(list);
                }
            });
        }

        function beforeSave(data) {

            let value = true;

            component.hideError();

            return value;
        }
        function afterSave(timeEntry, err) {

            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {
                component.afterSaved(timeEntry);

            }

        }

        function saveData() {

            let timeEntry = component.getEditTimeEntry();

            if (beforeSave(timeEntry)) {
                $aatl_ib.TimeEntryService.save(timeEntry, afterSave);
            }

        }


        function loadEmployeeDropdownOptions(dropdownControl) {

            $aatl_ib.EmployeeService.list(function (employees, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    let options = [{code: "", name: ""}];

                    employees.forEach((employee) => {
                        options.push({code: employee.number, name: employee.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(dropdownControl, options);

                }
            });
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
        function loadTimeCodeDropdownOptions(dropdownControl, clientNumber) {

            $aatl_ib.TimeCodeService.find({clientNumber: clientNumber}, function (timeCodes, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    let options = [{code: "", name: ""}];

                    timeCodes.forEach((timeCode) => {
                        options.push({code: timeCode.uid, name: timeCode.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(dropdownControl, options);

                    component.selectTimeCode();
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
            component.registerLoadEmployeeOptions(loadEmployeeDropdownOptions);
            component.registerLoadClientOptions(loadClientDropdownOptions);
            component.registerOnActionButtonClicked(onActionButtonClicked);
            component.registerLoadTimeCodeOptions(loadTimeCodeDropdownOptions);
            component.registerOnCriteriaChanged(crteriaChanged);
            component.setAfterInit(afterInit);
            component.init();
        };

        this.getComponent = function () {
            return component;
        };
    }

    return TimeSheetController;
}());




