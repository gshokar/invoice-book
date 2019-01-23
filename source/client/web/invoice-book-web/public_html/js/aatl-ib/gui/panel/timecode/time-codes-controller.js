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
                        options.push({code: client.number, name: client.name})
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
                        control.val("");
                    }
                });
            }
        }
        function afterInit() {
  
        }

        function beforeSave(data) {

            let value = true;

            component.hideError();

            return value;
        }
        function afterSave(client, err) {

            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                component.showError(err);
            } else {
                //component.setClient(client);

//                let currentTitle = title;
//                
//                title = 'Client - ' + client.name; 
//                
//                component.setTitle(title);
//                
//                $aatl_ib.viewController.mainController.updateActionItemText(currentTitle, title);
            }

        }

        function saveData() {

            let client = component.getClient();

            if (beforeSave(client)) {
                $aatl_ib.ClientService.save(client, afterSave);
            }

        }

        function onValueChanged(evt) {

            let $element = $(evt.target);

            if (component.isClientControl($element)) {

                let locationControl = component.getLocationControl();

                loadLocationDropdownOptions(locationControl, $element.val());
            }

            // set save enabled
            // set add disabled
        }

        this.init = function () {
            component.setAfterInit(afterInit);
            component.init();
            component.registerOnActionButtonClicked(onActionButtonClicked);
            component.registerOnFieldValueChanged(onValueChanged);
            component.registerLoadClientOptions(loadClientDropdownOptions);
        };

        this.getComponent = function () {
            return component;
        };
    }

    return TimeCodesController;
}());


