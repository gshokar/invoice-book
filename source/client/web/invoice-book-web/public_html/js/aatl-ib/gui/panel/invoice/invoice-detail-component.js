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

$aatl_ib.gui.InvoiceDetailComponent = (function () {
    function InvoiceDetailComponent(props) {

        let onActionButtonClicked = null;
        let afterInit = null;
        let isDataLoading = false;
        let loadClientOptions = undefined;
        let items = [];
        let changedItems = [];

        let component = new $aatl_ib.gui.Component(props);
        let errorComponent = new $aatl_ib.ErrorComponent({componentId: "invoiceErrors", parentComponent: component.getControl});
        let titleComponent = new $aatl_ib.gui.Component({componentId: "panelTitle", parentComponent: component.getControl});
        let numberField = new $aatl_ib.gui.Component({componentId: "number", parentComponent: component.getControl, componentName: "number"});
        let dateField = new $aatl_ib.gui.Component({componentId: "date", parentComponent: component.getControl, componentName: "date"});
        let clientField = new $aatl_ib.gui.Component({componentId: "client", parentComponent: component.getControl, componentName: "client"});
        let itemTable = new $aatl_ib.gui.TableComponent({componentId: "invoiceItemRecords", parentComponent: component.getControl});
        let itemRowEdit = new $aatl_ib.gui.InvoiceItemRowEdit();

        function getControl() {
            return component.getControl();
        }

        function getTitleControl() {
            return titleComponent.getControl();
        }

        function getNumberField() {
            return numberField.getControl();
        }

        function getDateField() {
            return dateField.getControl();
        }

        function getClientField() {
            return clientField.getControl();
        }

        function onFieldValueChanged(evt) {
            let id = $(evt.target).attr('id');
            
            switch(id){
                case clientField.getId():
                    let client = getClientField().val();
                    
                    setButtonActionEnabled("addItem", client.length > 0);
                    break;
            }
            
            if (isDataLoading === false) {
                setModified(true);
            }
        }

        function bindEvents() {

            getDateField().change((evt) => {
                onFieldValueChanged(evt);
            });

            getClientField().change((evt) => {
                onFieldValueChanged(evt);
            });

            getControl().find(".invoice-detail-action-btn-group button").click(function (evt) {

                let $element = $(evt.target);
                let action = $element.data("action");

                onActionButtonClicked(action);
            });
        }

        function updateElementIds(html) {
            let updatedHtml = errorComponent.updateElementId(html);

            let element = {html: updatedHtml, createNewId: true};

            element.html = titleComponent.updateElementId(element);
            element.html = itemTable.updateElementId(element);

            element.attributes = ['for'];

            element.html = numberField.updateElementId(element);
            element.html = dateField.updateElementId(element);
            element.html = clientField.updateElementId(element);

            return element.html;
        }

        function afterLoad() {

            bindEvents();

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }

            if (typeof loadClientOptions === 'function') {
                loadClientOptions(getClientField());
            }

            getDateField().datepicker({
                autoclose: true,
                format: "dd-M-yyyy"
            });

            setModified(false);
        }

        function setButtonActionEnabled(name, value) {
            getControl().find(".invoice-detail-action-btn-group button[data-action='" + name + "']").prop("disabled", !value);
        }

        function setModified(value) {

            if (typeof value === "boolean") {

                setButtonActionEnabled("save", value);
                component.setModified(value);
            }
        }

        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }

        function getNextLineNumber() {
            let lineNumber = 1;

            items.forEach(function (item) {
                if (item.lineNumber >= lineNumber) {
                    lineNumber = item.lineNumber + 1;
                }
            });

            return lineNumber;
        }

        function getTableRowData(item, index = 0) {

            let rowData = {keyValue: item.lineNumber,
                columnValues: []};

            rowData.columnValues.push(index + 1);
            rowData.columnValues.push(item.salesItem.itemType.name);
            rowData.columnValues.push(item.salesItem.name);
            rowData.columnValues.push(item.description);
            rowData.columnValues.push(item.quantity.toFixed(2));
            rowData.columnValues.push(item.salesItem.unit.name);
            rowData.columnValues.push(item.rate.toFixed(2));
            rowData.columnValues.push(item.amount.toFixed(2));

            return rowData;
        }

        function onItemTableRowDoubleClicked(keyValue) {

            if (itemRowEdit.getEditMode() === false) {

                let item = items.find(function (i) {
                    return i.lineNumber === keyValue;
                });

                if (item !== undefined) {
                    if (keyValue !== itemRowEdit.getItem().lineNumber) {

                        itemRowEdit.setRow(itemTable.getRowControl(keyValue)
                                , item);

                        //setElementsEnabled(false);

                        //setButtonActionEnabled("save", false);
                    }
                }
            }
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("invoice-detail", loadView);
        };

        this.setAfterInit = function (callback) {
            afterInit = callback;
        };

        this.setTitle = function (title) {

            getTitleControl().text(title);
        };

        this.registerOnActionButtonClicked = function (actionClicked) {
            onActionButtonClicked = actionClicked;
        };

        this.registerLoadClientOptions = function (loadOptions) {

            loadClientOptions = loadOptions;
        };
        
        this.registerLoadItemTypeOptions = function(loadOptions){
            itemRowEdit.registerLoadItemTypeOptions(loadOptions);
        };
        
        this.addItem = function () {
            let item = {
                lineNumber: getNextLineNumber(),
                uid: "",
                description: "",
                quantity: 0.0,
                rate: 0.00,
                amount: 0.00,
                taxAmount: 0.00,
                totalAmount: 0.00,
                taxes: [],
                salesItem: {
                    code: "",
                    name: "",
                    itemType: {uid: "", name: ""},
                    unit: {code: "", name: ""}
                }
            };

            items.push(item);
            changedItems.push(item);

            let rowData = getTableRowData(item, items.length - 1);

            itemTable.addRow(rowData);

            onItemTableRowDoubleClicked(item.lineNumber);
        };
        
        this.selectItemType = function(){
            itemRowEdit.selectItemType();
        };
    }

    return InvoiceDetailComponent;
}());
