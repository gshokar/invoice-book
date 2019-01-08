/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-25  GShokar         Created
 * =============================================================================
 */
"use strict";

$aatl_ib.gui.EmployeeDetailController = (function () {
    function EmployeeDetailController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.EmployeeDetailComponent({componentId: componentId, parentComponent: parentComponent});
        let title = "";
        let employeeNumber = null;

        function onToolbarItemClicked(toolbarItem) {

            switch (toolbarItem.typeCode) {
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Save:
                    saveData();
                    break;
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Close:
                    alert("Close");
                    break;
            }
        }
        ;

        function loadProvinceDropdownOptions() {
            $aatl_ib.LookupService.loadProvinces(function (provinces, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    $aatl_ib.utils.addDropdownOptions(component.getProvinceControl(), provinces);

                    component.selectProvince();

                }
            });
        }

        function afterInit() {

            loadProvinceDropdownOptions();

            component.setTitle(title);

            $aatl_ib.EmployeeService.get(employeeNumber, component.setEmployee);

        }

        function beforeSave(employee) {

            let value = true;
            
            component.hideError();
            
            return value;
        }
        function afterSave(employee, err) {

            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {
                component.setEmployee(employee);
                
                let currentTitle = title;
                
                title = 'Employee - ' + $aatl_ib.utils.getEmployeeName(employee); 
                
                component.setTitle(title);
                
                $aatl_ib.viewController.mainController.updateActionItemText(currentTitle, title);
            }

        }

        function saveData() {

            let employee = component.getEmployee();

            if (beforeSave(employee)) {
                $aatl_ib.EmployeeService.save(employee, afterSave);
            }

        }

        this.init = function () {
            component.setAfterInit(afterInit);
            component.init();
            component.registerOnToolbarItemClicked(onToolbarItemClicked);
        };

        this.getComponent = function () {
            return component;
        };

        this.setTitle = function (value) {
            title = value;
        };

        this.setEmployeeNumber = function (number) {
            employeeNumber = number;
        };
    }

    return EmployeeDetailController;
}());


