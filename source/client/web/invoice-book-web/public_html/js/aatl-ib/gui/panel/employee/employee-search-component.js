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

$aatl_ib.gui.EmployeeSearchComponent = (function () {
    function EmployeeSearchComponent(props) {

        let component = new $aatl_ib.gui.Component(props);

        let toolbar = null;
        let onToolbarItemClicked = null;
        let resultListTable = null;
        let resultList = [];
        let onTableRowDoubleClicked = null;

        function afterLoad() {
            toolbar = new $aatl_ib.gui.PanelToolbarComponent({componentId: "employeeSearchPanelToolbar", parentComponent: component.getControl});
            toolbar.init();
            toolbar.registerOnClickActionItem(onToolbarItemClicked);
            addToolbarItems();

            resultListTable = new $aatl_ib.gui.TableComponent({componentId: "employeeSearchResultList", parentComponent: component.getControl});

            resultListTable.setOnRowDoubleClicked(onResultListTableRowDoubleClicked);
        }

        function addToolbarItems() {

            toolbar.addNewActionItem("Find", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Find);
            toolbar.addNewActionItem("Open", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Open);
            toolbar.addNewActionItem("New", $aatl_ib.model.gui.PanelToolbarItemTypeCode.New);
            toolbar.addNewActionItem("Clear", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Clear);
        }
        ;

        function getEmployeeName() {
            return component.getControl().find("#employeeName").val().trim();
        }
       
        function getEmployeePhone() {
            return component.getControl().find("#employeePhone").val().trim();
        }

        function getTableRowsData() {

            let rowsData = [];

            $.each(resultList, function (index, employee) {
                let rowData = {keyValue: employee.number,
                    columnValues: []};

                rowData.columnValues.push(index + 1);
                rowData.columnValues.push(employee.number);
                rowData.columnValues.push(employee.lastName);
                rowData.columnValues.push(employee.firstName);
                rowData.columnValues.push(employee.contact.phone);
                rowData.columnValues.push(employee.address.city);
                rowData.columnValues.push(employee.address.province);

                rowsData.push(rowData);
            });

            return rowsData;
        }

        function updateTable() {
            resultListTable.clearRows();
            resultListTable.addRows(getTableRowsData());
        }
        function onResultListTableRowDoubleClicked(employeeNumber) {

            let employee = resultList.find(function (c) {
                return c.number === employeeNumber;
            });

            if (typeof onTableRowDoubleClicked === 'function') {
                onTableRowDoubleClicked(employee);
            }
        }

        this.init = function () {
            this.getComponent().load($aatl_ib.viewController.getViewUrl("employee-search"), afterLoad);

        };

        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            onToolbarItemClicked = actionItemClicked;
        };

        this.getComponent = function () {

            return component.getControl();

        };

        this.getCriteria = function () {

            return {name: getEmployeeName(),
                phone: getEmployeePhone()
            };
        };

        this.setResults = function (employees) {

            resultList = employees;

            updateTable();

        };

        this.registerOnTableRowDoubleClicked = function (action) {
            onTableRowDoubleClicked = action;
        };
    }

    return EmployeeSearchComponent;
}());
