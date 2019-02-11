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
        let loadClientOptions = undefined;
        let onCriteriaChanged = undefined;

        let errorComponent = new $aatl_ib.ErrorComponent({componentId: "timeSheetErrors", parentComponent: component.getControl});
        let titleComponent = new $aatl_ib.gui.Component({componentId: "panelTitle", parentComponent: component.getControl});
        let employeeField = new $aatl_ib.gui.Component({componentId: "employee", parentComponent: component.getControl, componentName: "employee"});
        let clientField = new $aatl_ib.gui.Component({componentId: "client", parentComponent: component.getControl, componentName: "client"});
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

        function getClientField() {
            return clientField.getControl();
        }

        function afterLoad() {

            bindEvents();

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }

            if (typeof loadEmployeeOptions === 'function') {
                loadEmployeeOptions(getEmpolyeeField());
            }

            if (typeof loadClientOptions === 'function') {
                loadClientOptions(getClientField());
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

                        timeEntryRowEdit.setRow(timeEntryTable.getRowControl(keyValue)
                                , timeEntry
                                , startDate
                                , getClientField().val());

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
            element.html = clientField.updateElementId(element);
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
            rowData.columnValues.push($aatl_ib.utils.displayDateFormat(timeEntry.date));
            rowData.columnValues.push(timeEntry.timeCode.name);
            rowData.columnValues.push($aatl_ib.utils.displayTimeFormat(timeEntry.startTime));
            rowData.columnValues.push($aatl_ib.utils.displayTimeFormat(timeEntry.endTime));
            rowData.columnValues.push(timeEntry.hours.toFixed(2));
            rowData.columnValues.push(timeEntry.approved);
            rowData.columnValues.push(timeEntry.charged);

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
            getClientField().prop("disabled", !value);

            setPrintEnabled();
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

            if (id === employeeField.getId()
                    || id === yearMonthField.getId()
                    || id === clientField.getId()) {

                refreshList();

            } else {
                timeEntryRowEdit.calcHours(id);

                // set save enabled
                setButtonActionEnabled("save", true);
            }
        }

        function createCriteria() {

            let employeeNumber = getEmpolyeeField().val();
            let yearMonthDate = getYearMonthField().datepicker('getDate');
            let clientNumber = getClientField().val();

            let criteria = {
                employeeNumber: employeeNumber,
                yearMonthDate: "",
                clientNumber: clientNumber
            }
            
            if(yearMonthDate instanceof Date){
                criteria.yearMonthDate = yearMonthDate.toISOString().slice(0, 10);
            }
            
            return criteria;
        }

        function refreshList() {
            
            let criteria = createCriteria();
            
            if (!$aatl_ib.utils.isStringEmpty(criteria.employeeNumber)
                    && !$aatl_ib.utils.isStringEmpty(criteria.yearMonthDate)) {

                if (typeof onCriteriaChanged === 'function') {

                    onCriteriaChanged(criteria);

                    setButtonActionEnabled("add", true);
                }
            } else {
                timeEntries = [];
                loadTimeEntries();
                setButtonActionEnabled("add", false);
            }

            setPrintEnabled();
        }

        function loadTimeEntries() {
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

        function setPrintEnabled() {
            let enable = timeEntryRowEdit.getEditMode() === false
                    && timeEntries.length > 0
                    && !$aatl_ib.utils.isStringEmpty(getClientField().val());

            setButtonActionEnabled("print", enable);
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("time-sheet", loadView);

        };

        this.registerOnActionButtonClicked = function (actionClicked) {
            onActionButtonClicked = actionClicked;
        };

        this.registerOnCriteriaChanged = function (criteriaChanged) {
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
                hours: 0,
                approved: false,
                charged: false,
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

        this.registerLoadClientOptions = function (loadOptions) {

            loadClientOptions = loadOptions;
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

        this.setTimeEntries = function (list) {
            timeEntries = list;

            loadTimeEntries();
        };

        this.selectTimeCode = function () {
            timeEntryRowEdit.selectTimeCode();
        };

        this.getCriteria = function () {
            return createCriteria();
        };
    }
    return TimeSheetComponent;
}());

