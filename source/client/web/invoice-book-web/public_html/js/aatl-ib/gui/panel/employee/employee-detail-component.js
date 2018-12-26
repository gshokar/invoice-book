/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-25  GShokar         Created
 * =============================================================================
 */
"use strict";

$aatl_ib.gui.EmployeeDetailComponent = (function () {
    function EmployeeDetailComponent(componentId, parentComponent) {

        let component = new $aatl_ib.gui.Component(componentId, parentComponent);
        let employee = null;
        let onToolbarItemClicked = null;
        let afterInit = null;
        let isFormLoading = false;

        let toolbar = new $aatl_ib.gui.PanelToolbarComponent("employeeDetailPanelToolbar", component.getControl, "employeeDetailPanelToolbar");
        let numberField = new $aatl_ib.gui.Component("employeeNumber", component.getControl, "employeeNumber");
        let lastNameField = new $aatl_ib.gui.Component("employeeLastName", component.getControl, "employeeLastName");
        let firstNameField = new $aatl_ib.gui.Component("employeeFirstName", component.getControl, "employeeFirstName");
        let address1Field = new $aatl_ib.gui.Component("employeeAddress1", component.getControl, "employeeAddress1");
        let address2Field = new $aatl_ib.gui.Component("employeeAddress2", component.getControl, "employeeAddress2");
        let cityField = new $aatl_ib.gui.Component("employeeCity", component.getControl, "employeeCity");
        let provinceField = new $aatl_ib.gui.Component("employeeProvince", component.getControl, "employeeProvince");
        let postalCodeField = new $aatl_ib.gui.Component("employeePostalCode", component.getControl, "employeePostalCode");
        let emailField = new $aatl_ib.gui.Component("employeeEmail", component.getControl, "employeeEmail");
        let phoneField = new $aatl_ib.gui.Component("employeePhoneNumber", component.getControl, "employeePhoneNumber");
        let birthDateField = new $aatl_ib.gui.Component("employeeBirthDate", component.getControl, "employeeBirthDate");

        function afterLoad() {
            toolbar.init();
            toolbar.registerOnClickActionItem(onToolbarItemClicked);
            addToolbarItems();
            bindEvents();

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }

            setModified(false);
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

        function getLastNameField() {
            return lastNameField.getControl();
        }

        function getFirstNameField() {
            return firstNameField.getControl();
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
        
        function getBirthDateField(){
            
            return birthDateField.getControl();
        }
        
        function getTitleControl() {
            return getControl().find("#panelTitle");
        }

        function getErrorControl() {
            return getControl().find(".alert-danger");
        }

        function setModified(value) {

            if (typeof value === "boolean") {

                toolbar.getActionItemControl($aatl_ib.model.gui.PanelToolbarItemTypeCode.Save).prop('disabled', !value);
                component.setModified(value);
            }
        }

        function bindEvents() {

            getLastNameField().change(() => {
                onFieldValueChanged();
            });
            
            getFirstNameField().change(() => {
                onFieldValueChanged();
            });
            
            getAddress1Field().change(() => {
                onFieldValueChanged();
            });
            getAddress2Field().change(() => {
                onFieldValueChanged();
            });
            getCityField().change(() => {
                onFieldValueChanged();
            });
            getProvinceField().change(() => {
                onFieldValueChanged();
            });
            getPostalCodeField().change(() => {
                onFieldValueChanged();
            });
            getPhoneField().change(() => {
                onFieldValueChanged();
            });
            getEmailField().change(() => {
                onFieldValueChanged();
            });
            
            getBirthDateField().change(() => {
                onFieldValueChanged();
            });
        }

        function onFieldValueChanged() {

            if (isFormLoading === false) {
                setModified(true);
            }
        }

        function onEmployeeChanged() {

            setModified(false);

            isFormLoading = true;

            if (employee === null || employee === undefined) {
                getNumberField().val("");
                getLastNameField().val("");
                getFirstNameField().val("");
                getAddress1Field().val("");
                getAddress2Field().val("");
                getCityField().val("");
                //getProvinceField().val("");
                getPostalCodeField().val("");
                getPhoneField().val("");
                getEmailField().val("");
                getBirthDateField().val("");
            } else {
                getNumberField().val(employee.number);
                getLastNameField().val(employee.lastName);
                getFirstNameField().val(employee.firstName);
                getBirthDateField().val(employee.birthDate);
                getAddress1Field().val(employee.address.address1);
                getAddress2Field().val(employee.address.address2);
                getCityField().val(employee.address.city);
                getProvinceField().val(employee.address.province);
                getPostalCodeField().val(employee.address.postalCode);
                getPhoneField().val(employee.contact.phone);
                getEmailField().val(employee.contact.email);
            }

            isFormLoading = false;
        }

        function updateElementIds(html) {
            let updatedHtml = toolbar.updateElementId(html, $aatl_ib.utils.createUniqueId());

            updatedHtml = numberField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = lastNameField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = firstNameField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = address1Field.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = address2Field.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = cityField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = provinceField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = postalCodeField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = emailField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = phoneField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            updatedHtml = birthDateField.updateElementId(updatedHtml, $aatl_ib.utils.createUniqueId(), true);
            
            return updatedHtml;
        }

        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }

        this.init = function () {
            $.get($aatl_ib.viewController.getViewUrl("employee-detail"), loadView);

        };

        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            onToolbarItemClicked = actionItemClicked;
        };

        this.getComponent = function () {

            return getControl();
        };

        this.getEmployee = function () {
            let employee = {
                number: getNumberField().val(),
                lastName: getLastNameField().val(),
                firstName: getFirstNameField().val(),
                birthDate: getBirthDateField().val(),
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

            return employee;
        };

        this.setEmployee = function (value) {
            employee = value;
            onEmployeeChanged();
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

        this.getProvinceControl = function () {

            return getProvinceField();
        };

        this.selectProvince = function () {

            if (employee !== undefined && employee !== null && employee.address !== undefined && employee.address !== null) {
                getProvinceField().val(employee.address.province);
            }
        };
    }

    return EmployeeDetailComponent;
}());



