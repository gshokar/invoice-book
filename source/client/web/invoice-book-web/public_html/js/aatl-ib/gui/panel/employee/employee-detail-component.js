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
    function EmployeeDetailComponent(props) {

        let component = new $aatl_ib.gui.Component(props);
        let employee = null;
        let onToolbarItemClicked = null;
        let afterInit = null;
        let isFormLoading = false;

        let toolbar = new $aatl_ib.gui.PanelToolbarComponent({componentId: "employeeDetailPanelToolbar", parentComponent: component.getControl, componentName: "employeeDetailPanelToolbar"});
        let numberField = new $aatl_ib.gui.Component({componentId: "employeeNumber", parentComponent: component.getControl, componentName: "employeeNumber"});
        let lastNameField = new $aatl_ib.gui.Component({componentId: "employeeLastName", parentComponent: component.getControl, componentName: "employeeLastName"});
        let firstNameField = new $aatl_ib.gui.Component({componentId: "employeeFirstName", parentComponent: component.getControl, componentName: "employeeFirstName"});
        let birthDateField = new $aatl_ib.gui.Component({componentId: "employeeBirthDate", parentComponent: component.getControl, componentName: "employeeBirthDate"});
        
        let addressComponent = new $aatl_ib.gui.AddressComponent({componentId: "address", parentComponent: component.getControl, componentName: "address", replaceComponent: true});
        let contactComponent = new $aatl_ib.gui.ContactComponent({componentId: "contact", parentComponent: component.getControl, componentName: "contact", replaceComponent: true});

        function afterLoad() {
            toolbar.init();
            toolbar.registerOnClickActionItem(onToolbarItemClicked);
            addToolbarItems();
            bindEvents();

            addressComponent.init();
            contactComponent.init();
            
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
            
            addressComponent.registerOnFieldValueChanged(onFieldValueChanged);
            contactComponent.registerOnFieldValueChanged(onFieldValueChanged);
            
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
                getBirthDateField().val("");
                
                addressComponent.setAddress(null);
                contactComponent.setContact(null);
                
            } else {
                getNumberField().val(employee.number);
                getLastNameField().val(employee.lastName);
                getFirstNameField().val(employee.firstName);
                getBirthDateField().val(employee.birthDate);
                
                addressComponent.setAddress(employee.address);
                contactComponent.setContact(employee.contact);
            }

            isFormLoading = false;
        }

        function updateElementIds(html) {

            let updatedHtml = toolbar.updateElementId(html, $aatl_ib.utils.createUniqueId());
            
            let element = {html: updatedHtml, createNewId: true, attributes: ['for']};
            
            element.html = numberField.updateElementId(element);
            element.html = lastNameField.updateElementId(element);
            element.html = firstNameField.updateElementId(element);
            element.html = birthDateField.updateElementId(element);

            return element.html;
        }

        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("employee-detail", loadView);

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
                address: addressComponent.getAddress(),
                contact: contactComponent.getContact()
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

            return addressComponent.getProvinceControl();
        };

        this.selectProvince = function () {

            if (employee !== undefined && employee !== null && employee.address !== undefined && employee.address !== null) {
                addressComponent.selectProvince(employee.address.province);
            }
        };
    }

    return EmployeeDetailComponent;
}());



