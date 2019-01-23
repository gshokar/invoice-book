/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-12  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.TimeCodesComponent = (function () {
    function TimeCodesComponent(props) {

        let component = new $aatl_ib.gui.Component(props);

        let timeCodes = [];
        let onActionButtonClicked = null;
        let afterInit = null;

        let errorComponent = new $aatl_ib.ErrorComponent({componentId: "clientErrors", parentComponent: component.getControl});
        let titleComponent = new $aatl_ib.gui.Component({componentId: "panelTitle", parentComponent: component.getControl});
        let timeCodeTable = new $aatl_ib.gui.TableComponent({componentId: "timeCodeList", parentComponent: component.getControl});
        let timeCodeRowEdit = new $aatl_ib.gui.TimeCodeRowEdit();

        function afterLoad() {

            bindEvents();

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }
        }

        function getControl() {
            return component.getControl();
        }

        function setButtonActionEnabled(name, value) {
            getControl().find(".time-codes-action-btn-group button[data-action='" + name + "']").prop("disabled", !value);
        }


        function bindEvents() {

            getControl().find(".time-codes-action-btn-group button").click(function (evt) {

                let $element = $(evt.target);
                let action = $element.data("action");

                onActionButtonClicked(action);
            });

            timeCodeTable.setOnRowDoubleClicked(onTimeCodeRowDoubleClicked)
        }

        function onTimeCodeRowDoubleClicked(keyValue) {

            let timeCode = timeCodes.find(function (tc) {
                return tc.uid == keyValue;
            });

            if (timeCode !== undefined) {
                if (keyValue !== timeCodeRowEdit.getTimeCode().uid) {
                    timeCodeRowEdit.setRow(timeCodeTable.getRowControl(keyValue), timeCode);
                }
            }
        }

        function updateElementIds(html) {
            let updatedHtml = errorComponent.updateElementId(html);

            let element = {html: updatedHtml, createNewId: true};

            element.html = titleComponent.updateElementId(element);

            return element.html;
        }

        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }

        function getTableRowData(timeCode) {

            let index = 0;
            let rowData = {keyValue: timeCode.uid,
                columnValues: []};

            rowData.columnValues.push(index + 1);
            rowData.columnValues.push(timeCode.name);
            rowData.columnValues.push(timeCode.client.name);
            rowData.columnValues.push(timeCode.clientLocation.name);
            rowData.columnValues.push(timeCode.active);
            rowData.columnValues.push(timeCode.chargeable);

            return rowData;
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("time-codes", loadView);

        };

        this.registerOnActionButtonClicked = function (actionClicked) {
            onActionButtonClicked = actionClicked;
        };

        this.getComponent = function () {

            return getControl();
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

        this.addTimeCode = function () {

            let timeCode = {
                uid: "",
                name: "",
                client: {
                    number: "",
                    name: ""
                },
                clientLocation: {
                    number: "",
                    name: ""
                },
                active: true,
                chargeable: true
            };

            timeCodes.push(timeCode);

            let rowData = getTableRowData(timeCode);

            timeCodeTable.addRow(rowData);


        };
        
        this.registerOnFieldValueChanged = function (valueChanged) {
            timeCodeRowEdit.registerOnFieldValueChanged(valueChanged);
        };
        
        this.isClientControl = function(control){
          return timeCodeRowEdit.isClientControl(control);
        };
        
        this.selectClient = function(){
            timeCodeRowEdit.selectClient();
        };
        
        this.registerLoadClientOptions = function(loadOptions){
          
            timeCodeRowEdit.registerLoadClientOptions(loadOptions);
        };
        
        this.getLocationControl = function(){
          return timeCodeRowEdit.getLocationControl();
        };
    }

    return TimeCodesComponent;
}());

