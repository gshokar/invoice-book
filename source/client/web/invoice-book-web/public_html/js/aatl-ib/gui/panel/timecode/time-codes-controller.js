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

$aatl_ib.gui.TimeCodesController = (function () {
    function TimeCodesController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.TimeCodesComponent({componentId: componentId, parentComponent: parentComponent});
//        let timeCodes = [];

        function onActionButtonClicked(action) {

            switch (action) {
                case "save":
                    saveData();
                    break;
                case "add":
                    component.addTimeCode();
                    break;
                case "cancel":
                    component.cancelTimeCodeEdit();
                    break;
            }
        }
        ;

        function loadClientDropdownOptions(clientControl) {

            $aatl_ib.ClientService.list(function (clients, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    let options = [{code: "", name: ""}];

                    clients.forEach((client) => {
                        options.push({code: client.number, name: client.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(clientControl, options);

                    component.selectClient();
                }
            });
        }
        function loadLocationDropdownOptions(control, clientNumber) {

            let options = [{code: "", name: ""}];

            control.empty();

            if ($aatl_ib.utils.isStringEmpty(clientNumber)) {
                $aatl_ib.utils.addDropdownOptions(control, options);
                control.val("");
            } else {
                $aatl_ib.ClientLocationService.list(clientNumber, function (locations, err) {

                    if (err !== undefined && err !== null) {
                        component.showError();
                    } else {

                        locations.forEach((location) => {
                            options.push({code: location.number, name: location.name})
                        });

                        $aatl_ib.utils.addDropdownOptions(control, options);

                        component.selectClientLocation();
                    }
                });
            }
        }
        
        function loadCompanyServiceDropdownOptions(dropdownControl) {

            $aatl_ib.LookupService.companyServices(function (list, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    let options = [{code: "", name: ""}];

                    list.forEach((entity) => {
                        options.push({code: entity.code, name: entity.name});
                    });

                    $aatl_ib.utils.addDropdownOptions(dropdownControl, options);

                    component.selectCompanyService();
                }
            });
        }
        
        function afterInit() {
            $aatl_ib.TimeCodeService.list(function (list, err) {
                if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                    component.showError(err);
                } else {
                    component.setTimeCodes(list);
                }
            });
        }

        function beforeSave(data) {

            let value = true;

            component.hideError();

            return value;
        }
        function afterSave(timeCode, err) {

            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {
                component.afterSaved(timeCode);

            }

        }

        function saveData() {

            let timeCode = component.getEditTimeCode();

            if (beforeSave(timeCode)) {
                $aatl_ib.TimeCodeService.save(timeCode, afterSave);
            }

        }

        function onValueChanged(evt) {

            let $element = $(evt.target);

            if (component.isClientControl($element)) {

                let locationControl = component.getLocationControl();

                loadLocationDropdownOptions(locationControl, $element.val());
            }

            // set save enabled
            component.setSaveEnabled(true);
        }

        this.init = function () {
            component.setAfterInit(afterInit);
            component.init();
            component.registerOnActionButtonClicked(onActionButtonClicked);
            component.registerOnFieldValueChanged(onValueChanged);
            component.registerLoadClientOptions(loadClientDropdownOptions);
            component.registerLoadCompanyServiceOptions(loadCompanyServiceDropdownOptions);
        };

        this.getComponent = function () {
            return component;
        };
    }

    return TimeCodesController;
}());


