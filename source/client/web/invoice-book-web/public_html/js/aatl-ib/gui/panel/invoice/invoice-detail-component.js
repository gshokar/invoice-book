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
        let changedItems = [];
        let invoice = undefined;

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

            switch (id) {
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

            itemTable.setOnRowDoubleClicked(onItemTableRowDoubleClicked);
            //itemRowEdit.registerOnFieldValueChanged(onFieldValueChanged);
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

            if (typeof loadClientOptions === 'function') {
                loadClientOptions(getClientField());
            }

            getDateField().datepicker({
                autoclose: true,
                format: "dd-M-yyyy"
            });

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }

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

            invoice.items.forEach(function (item) {
                if (item.lineNumber >= lineNumber) {
                    lineNumber = item.lineNumber + 1;
                }
            });

            return lineNumber;
        }

        function getTableRowData(item, index = 0) {

            let rowData = {keyValue: item.uid,
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

                let item = invoice.items.find(function (i) {
                    return i.uid === keyValue;
                });

                if (item !== undefined) {
                    if (keyValue !== itemRowEdit.getItem().uid) {

                        itemRowEdit.setRow(itemTable.getRowControl(keyValue)
                                , item);

                        //setElementsEnabled(false);

                        //setButtonActionEnabled("save", false);
                        setButtonActionEnabled("addItem", false);
                        setButtonActionEnabled("updateItem", false);
                        setButtonActionEnabled("editItem", false);
                        setButtonActionEnabled("cancel", true);
                        setButtonActionEnabled("print", false);
                    }
                }
            }
        }

        function setItemTableRows() {
            let rows = [];

            for (var i = 0; i < invoice.items.length; i++) {

                rows.push(getTableRowData(invoice.items[i], i));

            }

            itemTable.addRows(rows);
        }
        function setInvoiceItems() {
            itemTable.clearRows();
            setItemTableRows();
        }
        function onInvoiceChanged() {

            setModified(false);

            isDataLoading = true;

            if (invoice === null || invoice === undefined) {
                getNumberField().val("");
                getDateField().val("");
                getClientField().val("");

                setButtonActionEnabled("print", false);

            } else {
                getNumberField().val(invoice.number);
                getClientField().val(invoice.client.number);
                setButtonActionEnabled("print", !$aatl_ib.utils.isStringEmpty(invoice.number));

                let date = $aatl_ib.utils.parseDate(invoice.date);

                if (date instanceof Date) {
                    getDateField().datepicker('setDate', date);
                }

            }

            setInvoiceItems();
            updateInvoiceFooter();
            
            isDataLoading = false;

            //setButtonActionEnabled("save", false);
            setButtonActionEnabled("addItem", true);
            setButtonActionEnabled("updateItem", false);
            setButtonActionEnabled("editItem", true);
            setButtonActionEnabled("cancel", false);

        }

        function removeEditRow(keyValue) {

            let index = invoice.items.findIndex(function (tc) {
                return tc.uid === keyValue;
            });

            if (index >= 0) {
                invoice.items.splice(index, 1);
                itemTable.getRowControl(keyValue).remove();
            }
        }

        function resetTableRow(keyValue) {

            let index = invoice.items.findIndex(function (tc) {
                return tc.uid === keyValue;
            });

            let item = invoice.items[index];

            let rowData = getTableRowData(item, index);

            itemTable.replaceRow(keyValue, rowData);

        }

        function getTableFooterRow(text, amount){
            return '<tr>'
                    + '<td colspan="5">'
                    + '</td>'
                    + '<td colspan="2">'
                    + text
                    + '</td>'
                    + '<td>'
                    + amount.toFixed(2)
                    + '</td>'
                    + '</tr>';
        }
        function updateInvoiceFooter(){
            
            if(invoice 
                    && invoice.amount 
                    && invoice.totalAmount 
                    && invoice.amount >= 0
                    && invoice.totalAmount >= 0){
                
                let rowElements = getTableFooterRow("Sub Total:", invoice.amount);
                
                rowElements = rowElements + getTableFooterRow("Total:", invoice.totalAmount);
                
                itemTable.addFooter(rowElements);
                
            }else{
                itemTable.removeFooter();
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

        this.registerLoadItemTypeOptions = function (loadOptions) {
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

            invoice.items.push(item);
            changedItems.push(item);

            let rowData = getTableRowData(item, invoice.items.length - 1);

            itemTable.addRow(rowData);

            onItemTableRowDoubleClicked(item.uid);
        };

        this.editItem = function () {

        };

        this.updateItem = function () {

        };

        this.selectItemType = function () {
            itemRowEdit.selectItemType();
        };

        this.selectItem = function () {
            itemRowEdit.selectItem();
        };

        this.registerOnFieldValueChanged = function (valueChanged) {
            itemRowEdit.registerOnFieldValueChanged(valueChanged);
        };

        this.isItemTypeControl = function (control) {
            return itemRowEdit.isItemTypeControl(control);
        };

        this.isItemControl = function (control) {
            return itemRowEdit.isItemControl(control);
        };

        this.getItemControl = function () {
            return itemRowEdit.getItemControl();
        };

        this.setSaveEnabled = function (value) {
            setButtonActionEnabled("save", value);
        };

        this.setSalesItems = function (list) {
            itemRowEdit.setSalesItems(list);
        };

        this.itemChanged = function (value) {
            itemRowEdit.itemChanged(value);
        };

        this.showError = function (err) {
            errorComponent.show(err.messages);
        };

        this.isCalcAmount = function (control) {
            return itemRowEdit.isCalcAmount(control);
        };

        this.calcAmount = function () {
            itemRowEdit.calcAmount();
        };

        this.setInvoice = function (value) {
            invoice = value;

            onInvoiceChanged();
        };

        this.getEditInvoice = function () {
            let editInvoice = {
                client: {
                    number: "",
                    name: ""
                },
                items: []
            };

            editInvoice.number = invoice.number;

            let date = getDateField().datepicker('getDate');

            if (date instanceof Date) {
                // this will be yyyy-MM-dd
                editInvoice.date = date.toISOString().slice(0, 10);
            } else {
                editInvoice.date = "";
            }

            editInvoice.client.number = getClientField().val();

            let editItem = itemRowEdit.getItem();

            if (editItem && editItem.lineNumber) {
                editInvoice.items.push(editItem);
            }

            return editInvoice;
        };

        this.cancelItemEdit = function () {

            let editItem = itemRowEdit.getItem();

            if ($aatl_ib.utils.isStringEmpty(editItem.uid)) {

                removeEditRow(editItem.uid);
            } else {
                resetTableRow(editItem.uid);
            }

            itemRowEdit.reset();

            setButtonActionEnabled("addItem", true);

            errorComponent.hide();
        };

        this.hideError = function () {
            errorComponent.hide();
        };

        this.selectClient = function () {
            if (invoice && invoice !== null && invoice.client !== null) {
                getClientField().val(invoice.client.number);
            }
        };
        
        this.getInvoiceNumber = function () {
            return invoice.number;
        };
    }

    return InvoiceDetailComponent;
}());
