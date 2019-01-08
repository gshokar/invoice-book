/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-30  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.ClientLocationComponent = (function () {
    function ClientLocationComponent(props) {

        let component = new $aatl_ib.gui.Component(props);
        let location = null;
        let onToolbarItemClicked = null;
        let afterInit = null;
        let isFormLoading = false;
        let afterModified = undefined

        let numberField = new $aatl_ib.gui.Component({componentId: "clientLocationNumber", parentComponent: component.getControl, componentName: "clientLocationNumber"});
        let nameField = new $aatl_ib.gui.Component({componentId: "clientLocationName", parentComponent: component.getControl, componentName: "clientLocationName"});
        
        let addressComponent = new $aatl_ib.gui.AddressComponent({componentId: "locationAddress", parentComponent: component.getControl, componentName: "locationAddress", replaceComponent: true});
        let contactComponent = new $aatl_ib.gui.ContactComponent({componentId: "locationContact", parentComponent: component.getControl, componentName: "locationContact", replaceComponent: true});
        let errorComponent = new $aatl_ib.ErrorComponent({componentId: "clientLocationErrors", parentComponent: component.getControl});
        
        function afterLoad() {
            bindEvents();
            addressComponent.init();
            contactComponent.init();
            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }

            loadProvinceDropdownOptions();
            //    setModified(false);
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
            return getControl().find("#panelTitle");
        }

        function getErrorControl() {
            return getControl().find(".alert-danger");
        }

        function setModified(value) {

            if (typeof value === "boolean") {
                component.setModified(value);

                if (typeof afterModified === "function") {
                    afterModified(value);
                }
            }
        }

        function bindEvents() {

            getNameField().change(() => {
                onFieldValueChanged();
            });

            addressComponent.registerOnFieldValueChanged(onFieldValueChanged);
            contactComponent.registerOnFieldValueChanged(onFieldValueChanged);
        }

        function onFieldValueChanged() {

            if (isFormLoading === false) {
                setModified(true);
            }
        }

        function onLocationChanged() {

            setModified(false);
            isFormLoading = true;
            if (location === null || location === undefined) {

                getNumberField().val("");
                getNameField().val("");
                addressComponent.setAddress(null);
                contactComponent.setContact(null);

            } else {

                getNumberField().val(location.number);
                getNameField().val(location.name);
                addressComponent.setAddress(location.address);
                contactComponent.setContact(location.contact);

            }

            isFormLoading = false;
        }
     
        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }
        function loadProvinceDropdownOptions() {
            $aatl_ib.LookupService.loadProvinces(function (provinces, err) {

                if (err !== undefined && err !== null) {
                    component.showError();
                } else {
                    $aatl_ib.utils.addDropdownOptions(addressComponent.getProvinceControl(), provinces);
                    selectProvince();
                }
            });
        }

        function selectProvince() {

            if (location !== undefined && location !== null && location.address !== undefined && location.address !== null) {
                addressComponent.selectProvince(location.address.province);
            }
        }

        this.init = function () {
            //$.get($aatl_ib.viewController.getViewUrl("client-detail"), loadView);
            afterLoad();
        };
        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            onToolbarItemClicked = actionItemClicked;
        };
        this.getComponent = function () {

            return getControl();
        };
        this.getLocation = function () {
            return {
                number: getNumberField().val(),
                name: getNameField().val(),
                address: addressComponent.getAddress(),
                contact: contactComponent.getContact()
            };
        };
        this.setLocation = function (value) {
            location = value;
            onLocationChanged();
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

        this.updateElementIds = function (html) {
            let updatedHtml = errorComponent.updateElementId(html);

            let element = {html: updatedHtml, createNewId: true, attributes: ['for']};
            
            element.html = component.updateElementId(element);
            element.html = nameField.updateElementId(element);
            element.html = numberField.updateElementId(element);
            
            return element.html;
        };

        this.getNumber = function () {
            return getNumberField().val();
        };

        this.show = function (value) {

            if (value === true) {
                getControl().find("form").removeClass("d-none");
            } else {
                getControl().find("form").addClass("d-none");
            }
        };

        this.setAfterModified = function (value) {
            afterModified = value;
        };
    }

    return ClientLocationComponent;
}());

