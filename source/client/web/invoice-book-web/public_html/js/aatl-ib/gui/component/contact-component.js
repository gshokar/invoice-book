/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-28  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.ContactComponent = (function () {
    function ContactComponent(props) {

        let component = new $aatl_ib.gui.Component(props);
        let control = props.replaceComponent === true ? props.parentComponent : component.getControl;

        let emailField = new $aatl_ib.gui.Component({componentId: "email", parentComponent: control, componentName: "email"});
        let phoneField = new $aatl_ib.gui.Component({componentId: "phoneNumber", parentComponent: control, componentName: "phoneNumber"});

        let onFieldValueChanged = function () {};

        function getEmailField() {
            return emailField.getControl();
        }

        function getPhoneField() {
            return phoneField.getControl();
        }

        function bindEvents() {

            getEmailField().change(() => {
                onFieldValueChanged();
            });
            getPhoneField().change(() => {
                onFieldValueChanged();
            });

        }

        this.registerOnFieldValueChanged = function (valueChanged) {
            onFieldValueChanged = valueChanged;
        };

        this.getContact = function () {
            return {
                phone: getPhoneField().val(),
                email: getEmailField().val()
            };
        };

        this.setContact = function (contact) {
            if (contact === null || contact === undefined) {

                getPhoneField().val("");
                getEmailField().val("");

            } else {

                getPhoneField().val(contact.phone);
                getEmailField().val(contact.email);

            }
        };

        function updateElementIds(html) {

            let element = {html: html, createNewId: true, attributes: ['for']};

            element.html = emailField.updateElementId(element);
            element.html = phoneField.updateElementId(element);

            return element.html;
        }

        function loadView(html) {

            if (props.replaceComponent === true) {
                control().find(component.getControlId()).replaceWith(updateElementIds(html));
            } else {
                control().html(updateElementIds(html));
            }

            bindEvents();
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("contact", loadView);
        };
    }

    return ContactComponent;
}());

