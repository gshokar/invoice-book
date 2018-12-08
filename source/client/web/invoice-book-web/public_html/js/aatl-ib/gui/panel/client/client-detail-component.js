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
    function ClientDetailComponent(componentId, parentComponent) {

        let component = new $aatl_ib.gui.Component(componentId, parentComponent);
        let client = null;
        let onToolbarItemClicked = null;
        let afterInit = null;

        let toolbar = new $aatl_ib.gui.PanelToolbarComponent("clientDetailPanelToolbar", component.getControl, "clientDetailPanelToolbar");
        let numberField = new $aatl_ib.gui.Component("clientNumber", component.getControl, "clientNumber");
        let nameField = new $aatl_ib.gui.Component("clientName", component.getControl, "clientName");
        let address1Field = new $aatl_ib.gui.Component("clientAddress1", component.getControl, "clientAddress1");
        let address2Field = new $aatl_ib.gui.Component("clientAddress2", component.getControl, "clientAddress2");
        let cityField = new $aatl_ib.gui.Component("clientCity", component.getControl, "clientCity");
        let provinceField = new $aatl_ib.gui.Component("clientProvince", component.getControl, "clientProvince");
        let postalCodeField = new $aatl_ib.gui.Component("clientPostalCode", component.getControl, "clientPostalCode");
        let emailField = new $aatl_ib.gui.Component("clientEmail", component.getControl, "clientEmail");
        let phoneField = new $aatl_ib.gui.Component("clientPhoneNumber", component.getControl, "clientPhoneNumber");

        function afterLoad() {
            toolbar.init();
            toolbar.registerOnClickActionItem(onToolbarItemClicked);
            addToolbarItems();

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }
        }

        function getControl() {
            return component.getControl();
        }

        function addToolbarItems() {

            toolbar.addNewActionItem("Save", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Save);
            toolbar.addNewActionItem("Close", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Close);
        }

        function getNumberField() {
            return numberField.getControl();
        }

        function getNameField() {
            return nameField.getControl();
        }

        function getAddress1Field() {
            return address1Field.getControl();
        }

        function getAddress2Field() {
            return address2Field.getControl();
        }

        function getCityField() {
            return cityField.getControl();
        }

        function getProvinceField() {
            return provinceField.getControl();
        }

        function getPostalCodeField() {
            return postalCodeField.getControl();
        }

        function getEmailField() {
            return emailField.getControl();
        }

        function getPhoneField() {
            return phoneField.getControl();
        }

        function getTitleControl() {
            return getControl().find("#panelTitle");
        }

        function getErrorControl() {
            return getControl().find(".alert-danger");
        }

        function onClientChanged() {

            if (client === null || client === undefined) {
                getNumberField().val("");
                getNameField().val("");
                getAddress1Field().val("");
                getAddress2Field().val("");
                getCityField().val("");
                //getProvinceField().val("");
                getPostalCodeField().val("");
                getPhoneField().val("");
                getEmailField().val("");
            } else {
                getNumberField().val(client.number);
                getNameField().val(client.name);
                getAddress1Field().val(client.address.address1);
                getAddress2Field().val(client.address.address2);
                getCityField().val(client.address.city);
                getProvinceField().val(client.address.province);
                getPostalCodeField().val(client.address.postalCode);
                getPhoneField().val(client.contact.phone);
                getEmailField().val(client.contact.email);
            }
        }

        function updateElementIds(html) {
            let updatedHtml = toolbar.updateElementId(html, $aatl_ib.utils.createUniqueId());

            updatedHtml = numberField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = nameField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = address1Field.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = address2Field.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = cityField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = provinceField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = postalCodeField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = emailField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = phoneField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            
            return updatedHtml;
        }

        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }

        this.init = function () {
            $.get($aatl_ib.viewController.getViewUrl("client-detail"), loadView);

        };

        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            onToolbarItemClicked = actionItemClicked;
        };

        this.getComponent = function () {

            return getControl();
        };

        this.getClient = function () {
            let client = {
                number: getNumberField().val(),
                name: getNameField().val(),
                address: {
                    address1: getAddress1Field().val(),
                    address2: getAddress2Field().val(),
                    city: getCityField().val(),
                    province: getProvinceField().val(),
                    postalCode: getPostalCodeField().val()
                },
                contact: {
                    phone: getPhoneField().val(),
                    email: getEmailField().val()
                }
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
            let control = getErrorControl();

            control.empty();

            $.each(err.messages, function (index, message) {
                control.append(message);

                if (index < (err.messages.length - 1)) {
                    control.append("</br>");
                }
            });

            control.prop("hidden", false);
        };

        this.hideError = function () {
            getErrorControl().prop("hidden", true);
        };
        
        this.getProvinceControl = function(){
            
            return getProvinceField();
        }
    }

    return ClientDetailComponent;
}());

