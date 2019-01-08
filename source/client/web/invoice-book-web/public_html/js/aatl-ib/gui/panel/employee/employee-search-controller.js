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

$aatl_ib.gui.EmployeeSearchController = (function () {
    function EmployeeSearchController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.EmployeeSearchComponent({componentId: componentId, parentComponent: parentComponent});

        function onToolbarItemClicked(toolbarItem) {

            switch (toolbarItem.typeCode) {
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Find:
                    find();
                    break;
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.New:
                    createNewEmployee();
                    break;
                case $aatl_ib.model.gui.PanelToolbarItemTypeCode.Clear:
                    alert("Clear");
                    break;
            }
        }
        ;

        function onTableRowDoubleClicked(employee) {
           
            openEmployee(employee);
        }
        ;

        function afterFind(employees, err) {

            component.setResults(employees);
        }

        function find() {

            $aatl_ib.EmployeeService.find(component.getCriteria(), afterFind);
        }

        function createNewEmployee() {
            let actionItem = new $aatl_ib.gui.ActionItem({text: "Employee - New", typeCode: $aatl_ib.gui.ActionItemTypeCode.EmployeeDetail});
            $aatl_ib.viewController.mainController.openPanel(actionItem);
        }

        function openEmployee(employee) {

            if (typeof employee === 'object') {
                let actionItem = new $aatl_ib.gui.ActionItem({text: "Employee - " + $aatl_ib.utils.getEmployeeName(employee), typeCode: $aatl_ib.gui.ActionItemTypeCode.EmployeeDetail});
                actionItem.data = employee.number;
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

    return EmployeeSearchController;
}());