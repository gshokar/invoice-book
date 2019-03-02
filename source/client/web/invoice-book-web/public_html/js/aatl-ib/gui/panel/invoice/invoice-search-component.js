/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-19  GShokar         Created
 * =============================================================================
 */
"use strict";

$aatl_ib.gui.InvoiceSearchComponent = (function () {
    function InvoiceSearchComponent(props) {

        let component = new $aatl_ib.gui.Component(props);

        let onActionButtonClicked = null;
        let resultListTable = new $aatl_ib.gui.TableComponent({componentId: "invoiceSearchRecords", parentComponent: component.getControl});
        let resultList = [];
        let onTableRowDoubleClicked = null;
        let loadClientOptions = undefined;

        let errorComponent = new $aatl_ib.ErrorComponent({componentId: "invoiceSearchErrors", parentComponent: component.getControl});
        let titleComponent = new $aatl_ib.gui.Component({componentId: "panelTitle", parentComponent: component.getControl});
        let clientField = new $aatl_ib.gui.Component({componentId: "client", parentComponent: component.getControl, componentName: "client"});
        let dateField = new $aatl_ib.gui.Component({componentId: "date", parentComponent: component.getControl, componentName: "date"});
        let numberField = new $aatl_ib.gui.Component({componentId: "number", parentComponent: component.getControl, componentName: "number"});

        function afterLoad() {

            bindEvents();

            if (typeof loadClientOptions === 'function') {
                loadClientOptions(getClientField());
            }

            getDateField().datepicker({
                autoclose: true,
                format: "dd-M-yyyy"
            });

        }

        function bindEvents() {

            component.getControl().find(".invoice-search-action-btn-group button").click(function (evt) {

                let $element = $(evt.target);
                let action = $element.data("action");

                onActionButtonClicked(action);
            });

            resultListTable.setOnRowDoubleClicked(onResultListTableRowDoubleClicked);
        }

        function getClientField() {
            return clientField.getControl();
        }

        function getNumberField() {
            return numberField.getControl();
        }

        function getDateField() {
            return dateField.getControl();
        }

        function getTableRowsData() {

            let rowsData = [];

            $.each(resultList, function (index, invoice) {
                let rowData = {keyValue: invoice.number,
                    columnValues: []};

                rowData.columnValues.push(index + 1);
                rowData.columnValues.push(invoice.number);
                rowData.columnValues.push($aatl_ib.utils.displayDateFormat(invoice.date));
                rowData.columnValues.push(invoice.client.name);
                rowData.columnValues.push(invoice.amount.toFixed(2));
                rowData.columnValues.push(invoice.tax.toFixed(2));
                rowData.columnValues.push(invoice.totalAmount.toFixed(2));

                rowsData.push(rowData);
            });

            return rowsData;
        }

        function updateElementIds(html) {
            let updatedHtml = errorComponent.updateElementId(html);

            let element = {html: updatedHtml, createNewId: true};

            element.html = titleComponent.updateElementId(element);

            element.attributes = ['for'];

            element.html = numberField.updateElementId(element);
            element.html = clientField.updateElementId(element);
            element.html = dateField.updateElementId(element);

            return element.html;
        }

        function updateTable() {
            resultListTable.clearRows();
            resultListTable.addRows(getTableRowsData());
        }
        function onResultListTableRowDoubleClicked(number) {

            let invoice = resultList.find(function (rowData) {
                return rowData.number === number;
            });

            if (typeof onTableRowDoubleClicked === 'function') {
                onTableRowDoubleClicked(invoice);
            }
        }

        function loadView(html) {
            component.getControl().html(updateElementIds(html));
            afterLoad();
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("invoice-search", loadView);
        };

        this.registerOnActionButtonClicked = function (onClicked) {
            onActionButtonClicked = onClicked;
        };

        this.getComponent = function () {

            return component.getControl();

        };

        this.getCriteria = function () {

            return {
                number: getNumberField().val(),
                clientNumber: getClientField().val(),
                date: getDateField().datepicker('getDate')
            };
        };

        this.setResults = function (clients) {

            resultList = clients;

            updateTable();

        };

        this.registerOnTableRowDoubleClicked = function (action) {
            onTableRowDoubleClicked = action;
        };

        this.registerLoadClientOptions = function (loadOptions) {

            loadClientOptions = loadOptions;
        };
    }

    return InvoiceSearchComponent;
}());


