/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-27  GShokar         Created
 * =============================================================================
 */


"use strict";

$aatl_ib.gui.AddressComponent = (function () {
    function AddressComponent(props) {
               
        let component = new $aatl_ib.gui.Component(props);
        let control = props.replaceComponent === true ? props.parentComponent : component.getControl;
        
        let address1Field = new $aatl_ib.gui.Component({componentId: "address1", parentComponent: control, componentName: "address1"});
        let address2Field = new $aatl_ib.gui.Component({componentId: "address2", parentComponent: control, componentName: "address2"});
        let cityField = new $aatl_ib.gui.Component({componentId: "city", parentComponent: control, componentName: "city"});
        let provinceField = new $aatl_ib.gui.Component({componentId: "province", parentComponent: control, componentName: "province"});
        let postalCodeField = new $aatl_ib.gui.Component({componentId: "postalCode", parentComponent: control, componentName: "postalCode"});

        let onFieldValueChanged = function () {};

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

        function bindEvents() {

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
        }

        this.registerOnFieldValueChanged = function (valueChanged) {
            onFieldValueChanged = valueChanged;
        };

        this.getAddress = function () {
            return {
                address1: getAddress1Field().val(),
                address2: getAddress2Field().val(),
                city: getCityField().val(),
                province: getProvinceField().val(),
                postalCode: getPostalCodeField().val()
            };
        };

        this.setAddress = function (address) {
            if (address === null || address === undefined) {

                getAddress1Field().val("");
                getAddress2Field().val("");
                getCityField().val("");
                //getProvinceField().val("");
                getPostalCodeField().val("");

            } else {

                getAddress1Field().val(address.address1);
                getAddress2Field().val(address.address2);
                getCityField().val(address.city);
                getProvinceField().val(address.province);
                getPostalCodeField().val(address.postalCode);

            }
        };

        function updateElementIds(html) {

            let element = {html: html, createNewId: true, attributes: ['for']};
            
            element.html = address1Field.updateElementId(element);
            element.html = address2Field.updateElementId(element);
            element.html = cityField.updateElementId(element);
            element.html = provinceField.updateElementId(element);
            element.html = postalCodeField.updateElementId(element);

            return element.html;
        }

        this.getProvinceControl = function () {

            return getProvinceField();
        };

        this.selectProvince = function (province) {

            getProvinceField().val(province);

        };
        
        function loadView(html) {
            
            if(props.replaceComponent === true){
                control().find(component.getControlId()).replaceWith(updateElementIds(html));
            }else{
                control().html(updateElementIds(html));
            }
            
            bindEvents();
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("address", loadView);
        };
    }

    return AddressComponent;
}());
