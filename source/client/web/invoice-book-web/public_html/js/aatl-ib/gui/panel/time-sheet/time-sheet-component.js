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

$aatl_ib.gui.TimeSheetComponent = (function () {
    function TimeSheetComponent(props) {

        let component = new $aatl_ib.gui.Component(props);

        let timeEntries = [];
        let onActionButtonClicked = null;
        let afterInit = null;
        let loadEmployeeOptions = undefined;
        let onCriteriaChanged = undefined;
        
        let errorComponent = new $aatl_ib.ErrorComponent({componentId: "timeSheetErrors", parentComponent: component.getControl});
        let titleComponent = new $aatl_ib.gui.Component({componentId: "panelTitle", parentComponent: component.getControl});
        let employeeField = new $aatl_ib.gui.Component({componentId: "employee", parentComponent: component.getControl, componentName: "employee"});
        let yearMonthField = new $aatl_ib.gui.Component({componentId: "timesheetYearMonth", parentComponent: component.getControl, componentName: "timesheetYearMonth"});
        let timeEntryTable = new $aatl_ib.gui.TableComponent({componentId: "timeSheeTimeRecords", parentComponent: component.getControl});
        let timeEntryRowEdit = new $aatl_ib.gui.TimeSheetTimeEntryRowEdit();

        function getControl() {
            return component.getControl();
        }

        function getEmpolyeeField() {
            return employeeField.getControl();
        }

        function getYearMonthField() {
            return yearMonthField.getControl();
        }

        function afterLoad() {

            bindEvents();

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }

            if (typeof loadEmployeeOptions === 'function') {
                loadEmployeeOptions(getEmpolyeeField());
            }

            getYearMonthField().datepicker({
                autoclose: true,
                format: "MM yyyy",
                startView: "months",
                minViewMode: "months"
            });
        }

        function bindEvents() {

            getControl().find(".time-sheet-action-btn-group button").click(function (evt) {

                let $element = $(evt.target);
                let action = $element.data("action");

                onActionButtonClicked(action);
            });

            timeEntryTable.setOnRowDoubleClicked(onTimeEntryRowDoubleClicked);
            timeEntryRowEdit.registerOnFieldValueChanged(onValueChanged);

            getControl().find(":input").change(function (evt) {

                onValueChanged(evt);

            });
        }

        function onTimeEntryRowDoubleClicked(keyValue) {

            if (timeEntryRowEdit.getEditMode() === false) {

                let timeEntry = timeEntries.find(function (te) {
                    return te.uid === keyValue;
                });

                if (timeEntry !== undefined) {
                    if (keyValue !== timeEntryRowEdit.getTimeEntry().uid) {
                        let startDate = getYearMonthField().datepicker('getDate');

                        timeEntryRowEdit.setRow(timeEntryTable.getRowControl(keyValue), timeEntry, startDate);

                        setElementsEnabled(false);

                        setButtonActionEnabled("save", false);
                    }
                }
            }
        }
        function updateElementIds(html) {
            let updatedHtml = errorComponent.updateElementId(html);

            let element = {html: updatedHtml, createNewId: true};

            element.html = titleComponent.updateElementId(element);

            element.attributes = ['for'];

            element.html = employeeField.updateElementId(element);
            element.html = yearMonthField.updateElementId(element);

            return element.html;
        }
        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }

        function getTableRowData(timeEntry, index = 0) {

            let rowData = {keyValue: timeEntry.uid,
                columnValues: []};

            rowData.columnValues.push(index + 1);
            rowData.columnValues.push(timeEntry.date);
            rowData.columnValues.push(timeEntry.timeCode.name);
            rowData.columnValues.push(timeEntry.startTime);
            rowData.columnValues.push(timeEntry.endTime);
            rowData.columnValues.push(timeEntry.hours);

            return rowData;
        }

        function setButtonActionEnabled(name, value) {
            getControl().find(".time-sheet-action-btn-group button[data-action='" + name + "']").prop("disabled", !value);
        }

        function setElementsEnabled(value) {

            setButtonActionEnabled("add", value);
            setButtonActionEnabled("cancel", !value);
            setButtonActionEnabled("save", !value);

            getEmpolyeeField().prop("disabled", !value);
            getYearMonthField().prop("disabled", !value);
        }

        function removeEditRow(keyValue) {

            let index = timeEntries.findIndex(function (tc) {
                return tc.uid === keyValue;
            });

            if (index >= 0) {
                timeEntries.splice(index, 1);
                timeEntryTable.getRowControl(keyValue).remove();
            }
        }

        function resetTableRow(keyValue) {

            let index = timeEntries.findIndex(function (tc) {
                return tc.uid === keyValue;
            });

            let timeEntry = timeEntries[index];

            let rowData = getTableRowData(timeEntry, index);

            timeEntryTable.replaceRow(keyValue, rowData);

        }

        function onValueChanged(evt) {

            let id = $(evt.target).attr('id');

            if (id === employeeField.getId() || id === yearMonthField.getId()) {
                refreshList();
            }else{
                timeEntryRowEdit.calcHours(id);
                
                // set save enabled
                setButtonActionEnabled("save", true);
            }
        }
        
        function refreshList(){
            let employeeNumber = getEmpolyeeField().val();
            let yearMonthDate = getYearMonthField().datepicker('getDate');
            
            if(yearMonthDate instanceof Date && !$aatl_ib.utils.isStringEmpty(employeeNumber)){
                
                if(typeof onCriteriaChanged === 'function'){
                    
                    onCriteriaChanged(employeeNumber, yearMonthDate.toISOString().slice(0, 10));
                }
            }else{
                timeEntries = [];
                loadTimeEntries();
                setButtonActionEnabled("add", false);
            }
        }
        
        function loadTimeEntries(){
            timeEntryTable.clearRows();
            setTableRows();
        }
        
        function setTableRows() {

            let rows = [];

            for (var i = 0; i < timeEntries.length; i++) {

                rows.push(getTableRowData(timeEntries[i], i));

            }

            timeEntryTable.addRows(rows);
        }
        
        this.init = function () {
            $aatl_ib.ViewService.getViewContent("time-sheet", loadView);

        };

        this.registerOnActionButtonClicked = function (actionClicked) {
            onActionButtonClicked = actionClicked;
        };

        this.registerOnCriteriaChanged = function(criteriaChanged){
            onCriteriaChanged = criteriaChanged;
        };
        
        this.setAfterInit = function (callback) {
            afterInit = callback;
        };

        this.showError = function (err) {
            errorComponent.show(err.messages);
        };

        this.hideError = function () {
            errorComponent.hide();
        };

        this.addTimeEntry = function () {

            let timeEntry = {
                uid: "",
                date: "",
                startTime: "",
                endTime: "",
                hours: "",
                timeCode: {
                    uid: "",
                    name: ""
                },
                employee: {
                    number: getEmpolyeeField().val()
                }
            };

            timeEntries.push(timeEntry);

            let rowData = getTableRowData(timeEntry, timeEntries.length - 1);

            timeEntryTable.addRow(rowData);

            onTimeEntryRowDoubleClicked(timeEntry.uid);

        };

        this.registerLoadEmployeeOptions = function (loadOptions) {

            loadEmployeeOptions = loadOptions;
        };

        this.registerLoadTimeCodeOptions = function (loadOptions) {

            timeEntryRowEdit.registerLoadTimeCodeOptions(loadOptions);
        };

        this.setSaveEnabled = function (value) {
            setButtonActionEnabled("save", value);
        };

        this.getEditTimeEntry = function () {
            return timeEntryRowEdit.getTimeEntry();
        };
     
        this.cancelTimeEntryEdit = function () {
            let editTimeEntry = timeEntryRowEdit.getTimeEntry();

            if ($aatl_ib.utils.isStringEmpty(editTimeEntry.uid)) {

                removeEditRow(editTimeEntry.uid);
            } else {
                resetTableRow(editTimeEntry.uid);
            }

            timeEntryRowEdit.reset();

            setElementsEnabled(true);

            errorComponent.hide();
        };

        this.afterSaved = function (timeEntry) {
            let editTimeEntry = timeEntryRowEdit.getTimeEntry();

            let index = timeEntries.findIndex(function (tc) {
                return tc.uid === editTimeEntry.uid;
            });

            timeEntries[index] = timeEntry;

            let rowData = getTableRowData(timeEntry, index);

            timeEntryTable.replaceRow(editTimeEntry.uid, rowData);

            timeEntryRowEdit.reset();

            setElementsEnabled(true);

            errorComponent.hide();
        };
        
        this.setTimeEntries = function(list){
            timeEntries = list;
            
            loadTimeEntries();
        };
    }
    return TimeSheetComponent;
}());

