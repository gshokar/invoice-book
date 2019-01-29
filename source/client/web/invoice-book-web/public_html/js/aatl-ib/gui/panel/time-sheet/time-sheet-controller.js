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

        function crteriaChanged(employeeNumber, yearMonthDate) {
            $aatl_ib.TimeEntryService.find({employeeNumber: employeeNumber, yearMonthDate: yearMonthDate}, function (list, err) {
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

        function loadTimeCodeDropdownOptions(dropdownControl) {

            $aatl_ib.TimeCodeService.list(function (timeCodes, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    let options = [{code: "", name: ""}];

                    timeCodes.forEach((timeCode) => {
                        options.push({code: timeCode.uid, name: timeCode.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(dropdownControl, options);

                }
            });
        }

        this.init = function () {
            component.setAfterInit(afterInit);
            component.registerLoadEmployeeOptions(loadEmployeeDropdownOptions);
            component.init();
            component.registerOnActionButtonClicked(onActionButtonClicked);
            component.registerLoadTimeCodeOptions(loadTimeCodeDropdownOptions);
            component.registerOnCriteriaChanged(crteriaChanged);
        };

        this.getComponent = function () {
            return component;
        };
    }

    return TimeSheetController;
}());




