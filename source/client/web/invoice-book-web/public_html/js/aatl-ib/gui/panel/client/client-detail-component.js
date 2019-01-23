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

$aatl_ib.gui.ClientDetailComponent = (function () {
    function ClientDetailComponent(props) {

        let component = new $aatl_ib.gui.Component(props);
        let client = null;
        let onActionButtonClicked = null;
        let afterInit = null;
        let isFormLoading = false;

        let numberField = new $aatl_ib.gui.Component({componentId: "clientNumber", parentComponent: component.getControl, componentName: "clientNumber"});
        let nameField = new $aatl_ib.gui.Component({componentId: "clientName", parentComponent: component.getControl, componentName: "clientName"});

        let addressComponent = new $aatl_ib.gui.AddressComponent({componentId: "address", parentComponent: component.getControl, componentName: "address", replaceComponent: true});
        let contactComponent = new $aatl_ib.gui.ContactComponent({componentId: "contact", parentComponent: component.getControl, componentName: "contact", replaceComponent: true});
        let locationsComponent = new $aatl_ib.gui.ClientLocationsComponent({componentId: "clientLocations", parentComponent: component.getControl, componentName: "clientLocations"});
        let errorComponent = new $aatl_ib.ErrorComponent({componentId: "clientErrors", parentComponent: component.getControl});
        let titleComponent = new $aatl_ib.gui.Component({componentId: "panelTitle", parentComponent: component.getControl});
        
        function afterLoad() {

            bindEvents();

            addressComponent.init();
            contactComponent.init();
            locationsComponent.init();

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }

            setModified(false);
        }

        function getControl() {
            return component.getControl();
        }
   
        function getNumberField() {
            return numberField.getControl();
        }

        function getNameField() {
            return nameField.getControl();
        }

        function getTitleControl() {
            return titleComponent.getControl();
        }
        
        function setButtonActionEnabled(name, value) {
            getControl().find(".client-action-btn-group button[data-action='" + name + "']").prop("disabled", !value);
        }
        
        function setModified(value) {

            if (typeof value === "boolean") {

                setButtonActionEnabled("save", value);
                component.setModified(value);
            }
        }

        function bindEvents() {

            getNameField().change(() => {
                onFieldValueChanged();
            });

            getControl().find(".client-action-btn-group button").click(function (evt) {

                let $element = $(evt.target);
                let action = $element.data("action");

                onActionButtonClicked(action);
            });

            addressComponent.registerOnFieldValueChanged(onFieldValueChanged);
            contactComponent.registerOnFieldValueChanged(onFieldValueChanged);
        }

        function onFieldValueChanged() {

            if (isFormLoading === false) {
                setModified(true);
            }
        }

        function onClientChanged() {

            setModified(false);

            isFormLoading = true;

            if (client === null || client === undefined) {
                getNumberField().val("");
                getNameField().val("");

                addressComponent.setAddress(null);
                contactComponent.setContact(null);
                
                locationsComponent.setClientNumber("");
            } else {
                getNumberField().val(client.number);
                getNameField().val(client.name);

                addressComponent.setAddress(client.address);
                contactComponent.setContact(client.contact);
                locationsComponent.setClientNumber(client.number);
            }

            isFormLoading = false;
        }

        function updateElementIds(html) {
            let updatedHtml = errorComponent.updateElementId(html);
            
            updatedHtml = locationsComponent.updateElementIds(updatedHtml);

            let element = {html: updatedHtml, createNewId: true};
            
            element.html = titleComponent.updateElementId(element);
            
            element.attributes = ['for'];
            
            element.html = numberField.updateElementId(element);
            element.html = nameField.updateElementId(element);

            return element.html;
        }

        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("client-detail", loadView);

        };

        this.registerOnActionButtonClicked = function (actionClicked) {
            onActionButtonClicked = actionClicked;
        };

        this.getComponent = function () {

            return getControl();
        };

        this.getClient = function () {
            let client = {
                number: getNumberField().val(),
                name: getNameField().val(),
                address: addressComponent.getAddress(),
                contact: contactComponent.getContact()
            };

            return client;
        };

        this.setClient = function (value) {
            client = value;
            onClientChanged();
        };

        this.setTitle = function (title) {

            getTitleControl().text(title);
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

        this.getProvinceControl = function () {

            return addressComponent.getProvinceControl();
        };

        this.selectProvince = function () {

            if (client !== undefined && client !== null && client.address !== undefined && client.address !== null) {
                addressComponent.selectProvince(client.address.province);
            }
        };
    }

    return ClientDetailComponent;
}());

