/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-22  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.ClientDetailController = (function () {
    function ClientDetailController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.ClientDetailComponent(componentId, parentComponent);
        let title = "";
        let clientNumber = null;

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

            $aatl_ib.ClientService.get(clientNumber, component.setClient);

        }

        function beforeSave(client) {

            let value = true;
            
            component.hideError();
            
            return value;
        }
        function afterSave(client, err) {

            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {
                component.setClient(client);
            }

        }

        function saveData() {

            let client = component.getClient();

            if (beforeSave(client)) {
                $aatl_ib.ClientService.save(client, afterSave);
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

        this.setClientNumber = function (number) {
            clientNumber = number;
        };
    }

    return ClientDetailController;
}());
