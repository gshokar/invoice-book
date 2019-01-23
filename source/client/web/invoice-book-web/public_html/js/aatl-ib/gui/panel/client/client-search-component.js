/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-11  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.ClientSearchComponent = (function () {
    function ClientSearchComponent(props) {

        let component = new $aatl_ib.gui.Component(props);

        let onActionButtonClicked = null;
        let resultListTable = new $aatl_ib.gui.TableComponent({componentId: "clientSearchResultList", parentComponent: component.getControl});
        let resultList = [];
        let onTableRowDoubleClicked = null;
        
        function afterLoad() {
            component.getControl().find(".client-search-action-btn-group button").click(function (evt) {

                let $element = $(evt.target);
                let action = $element.data("action");

                onActionButtonClicked(action);
            });

            resultListTable.setOnRowDoubleClicked( onResultListTableRowDoubleClicked );
        }
       
        function getClientName() {
            return component.getControl().find("#clientName").val().trim();
        }

        function getClientPhone() {
            return component.getControl().find("#clientPhone").val().trim();
        }

        function getTableRowsData() {

            let rowsData = [];

            $.each(resultList, function (index, client) {
                let rowData = {keyValue: client.number,
                    columnValues: []};

                rowData.columnValues.push(index + 1);
                rowData.columnValues.push(client.number);
                rowData.columnValues.push(client.name);
                rowData.columnValues.push(client.contact.phone);
                rowData.columnValues.push(client.address.city);
                rowData.columnValues.push(client.address.province);

                rowsData.push(rowData);
            });

            return rowsData;
        }

        function updateTable() {
            resultListTable.clearRows();
            resultListTable.addRows(getTableRowsData());
        }
        function onResultListTableRowDoubleClicked(clientNumber) {

            let client = resultList.find(function (c) {
                return c.number === clientNumber;
            });

            if (typeof onTableRowDoubleClicked === 'function') {
                onTableRowDoubleClicked(client);
            }
        }

        this.init = function () {
            this.getComponent().load($aatl_ib.viewController.getViewUrl("client-search"), afterLoad);

        };

        this.registerOnActionButtonClicked = function (onClicked) {
            onActionButtonClicked = onClicked;
        };

        this.getComponent = function () {

            return component.getControl();

        };

        this.getCriteria = function () {

            return {name: getClientName(),
                phone: getClientPhone()
            };
        };

        this.setResults = function (clients) {

            resultList = clients;

            updateTable();

        };

        this.registerOnTableRowDoubleClicked = function (action) {
            onTableRowDoubleClicked = action;
        };
    }

    return ClientSearchComponent;
}());
