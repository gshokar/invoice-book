/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-01  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.ClientLocationsComponent = (function () {
    function ClientLocationsComponent(props) {

        let component = new $aatl_ib.gui.Component(props);
        let list = [];
        let clientNumber = "";

        let actionGroupComponent = new $aatl_ib.gui.ActionGroupComponent({componentId: "clientLocationList", parentComponent: getControl});
        let locationComponent = new $aatl_ib.gui.ClientLocationComponent({componentId: "clientLocation", parentComponent: getControl, componentName: "clientLocation"});

        let buttonActions = {add: "add", save: "save", remove: "remove"};

        function getControl() {
            return component.getControl();
        }

        function addLocationActionItem(location) {
            let actionItem = new $aatl_ib.gui.ActionItem({text: location.name === "" ? "New Location" : location.name, data: location.number, createId: true});

            actionGroupComponent.addActionItem(actionItem);

            actionGroupComponent.selectActionItem(actionItem);

        }

        function addNewLocation() {
            let location = {
                clientNumber: clientNumber,
                name: "",
                number: "",
                address: {
                    address1: "",
                    address2: "",
                    city: "",
                    province: "ON",
                    postalCode: ""
                },
                contact: {
                    phone: "",
                    email: ""
                }};
            list.push(location);

            addLocationActionItem(location);
        }

        function getLocation(number) {

            return list.find(function (location) {
                return number === location.number;
            });
        }

        function setButtonActionEnabled(name, value) {
            getControl().find(".btn-group button[data-action='" + name + "']").prop("disabled", !value);
        }

        function onLocationActionItemClicked(actionItem) {

            if (actionItem.data !== locationComponent.getNumber()) {

                let location = getLocation(actionItem.data);

                locationComponent.setLocation(location);

                if (location.number === "") {
                    setButtonActionEnabled(buttonActions.add, false);
                } else {
                    setButtonActionEnabled(buttonActions.add, true);
                }
            }

            locationComponent.show(true);
        }

        function onActionButtonClicked(action) {
            switch (action) {
                case buttonActions.add:
                    addNewLocation();
                    break;
                case buttonActions.save:
                    saveData();
                    break;
            }
        }

        function afterSave(location, err) {
            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                locationComponent.showError(err);
            } else {

                let selectedActionItem = actionGroupComponent.getSelectedActionItem();

                locationComponent.setLocation(location);

                let locationIndex = list.findIndex(function (obj) {
                    return obj.number === selectedActionItem.data;
                });

                list[locationIndex] = location;

                selectedActionItem.data = location.number;
                selectedActionItem.text = location.name;

                actionGroupComponent.updateActionItemText(selectedActionItem);

                setButtonActionEnabled(buttonActions.add, true);
            }
        }

        function saveData() {
            let location = locationComponent.getLocation();

            locationComponent.hideError();

            $aatl_ib.ClientLocationService.save(location, afterSave);

        }
        function bindEvents() {
            getControl().find(".btn-group button").click(function (evt) {

                let $element = $(evt.target);
                let action = $element.data("action");

                onActionButtonClicked(action);
            });

            actionGroupComponent.registerOnClickActionItem(onLocationActionItemClicked);
            locationComponent.setAfterModified(afterLocationModified);
        }

        function afterLocationModified(value) {
            setButtonActionEnabled(buttonActions.save, value);
        }

        function loadLocations(locations, err) {

            if (err !== undefined && Array.isArray(err.messages) && err.messages.length > 0) {
                locationComponent.showError(err);
            } else {
                setButtonActionEnabled(buttonActions.add, true);
                list = locations;

                if (list !== undefined && Array.isArray(list) && list.length > 0) {
                    list.forEach(function (location) {
                        addLocationActionItem(location);
                    });
                }
            }
        }

        function onClientNumberChanged() {

            actionGroupComponent.clearActionItems();
            locationComponent.hideError();
            locationComponent.show(false);
            setButtonActionEnabled(buttonActions.add, false);

            if ($aatl_ib.utils.isStringEmpty(clientNumber)) {
                list = [];
            } else {
                $aatl_ib.ClientLocationService.list(clientNumber, loadLocations);
            }
        }

        this.init = function () {
            //$.get($aatl_ib.viewController.getViewUrl("client-detail"), loadView);
            locationComponent.init();

            bindEvents();
        };

        this.getComponent = function () {

            return getControl();
        };

        this.updateElementIds = function (html) {
            let element = {html: html, createNewId: true};

            element.html = component.updateElementId(element);
            element.html = actionGroupComponent.updateElementId(element);

            element.newId = $aatl_ib.utils.createUniqueId();
            element.attributes = ['id', 'data-target', 'aria-controls'];

            element.attributes.forEach(
                    function (attr) {
                        element.html = $aatl_ib.utils.replaceElementAttribute(element.html, attr, 'clientLocationsBody', element.newId);
                    });

            element.html = locationComponent.updateElementIds(element.html);

            return element.html;
        };

        this.setClientNumber = function (number) {

            if (clientNumber !== number) {
                clientNumber = number;
                onClientNumberChanged();
            }
        };
    }

    return ClientLocationsComponent;
}());