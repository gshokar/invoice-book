/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-19  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.TimeCodeRowEdit = (function () {
    function TimeCodeRowEdit() {
        let timeCode = {};

        let nameFieldId = "";
        let clientFieldId = "";
        let locationFieldId = "";
        let activeFieldId = "";
        let chargeableFieldId = "";
        let companyServiceFieldId = "";

        let rowElement = undefined;
        let editMode = false;
        let locationNumber = "";

        let onFieldValueChanged = undefined;
        let loadClientOptions = undefined;
        let loadCompanyServiceOptions = undefined;

        function getNameElement() {

            nameFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<input type="text" class="form-control" id="' + nameFieldId + '" placeholder="code name">';

            return row;
        }

        function getClientElement() {

            clientFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<select class="form-control" id="' + clientFieldId + '"><option selected></option></select>';

            return row;
        }

        function getLocationElement() {

            locationFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<select class="form-control" id="' + locationFieldId + '"><option selected></option></select>';

            return row;
        }

        function getActiveElement() {

            activeFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<input type="checkbox" class="form-control" id="' + activeFieldId + '">';

            return row;
        }

        function getChargeableElement() {

            chargeableFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<input type="checkbox" class="form-control" id="' + chargeableFieldId + '">';

            return row;
        }

        function getCompanyServiceElement() {

            companyServiceFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<select class="form-control" id="' + companyServiceFieldId + '"><option selected></option></select>';

            return row;
        }

        function setEditColumnElements(rowControl) {
            let columns = rowControl.children();

            for (var i = 1; i < columns.length; i++) {

                let $element = $(columns[i]);

                $element.empty();

                switch (i) {
                    case 1:
                        $element.append(getNameElement());
                        break;
                    case 2:
                        $element.append(getCompanyServiceElement());
                        break;
                    case 3:
                        $element.append(getClientElement());
                        break;
                    case 4:
                        $element.append(getLocationElement());
                        break;
                    case 5:
                        $element.append(getActiveElement());
                        break;
                    case 6:
                        $element.append(getChargeableElement());
                        break;
                }

            }
        }

        function getClientControl(rowControl) {
            return rowControl.find('#' + clientFieldId);
        }

        function getLocationControl(rowControl) {
            return rowControl.find('#' + locationFieldId);
        }

        function getCompanyServiceControl(rowControl) {
            return rowControl.find('#' + companyServiceFieldId);
        }

        function setClientDropdown(rowControl) {

            if (typeof loadClientOptions === 'function') {
                loadClientOptions(getClientControl(rowControl));
            }
        }

        function setCompanyServiceDropdown(rowControl) {

            if (typeof loadCompanyServiceOptions === 'function') {
                loadCompanyServiceOptions(getCompanyServiceControl(rowControl));
            }
        }

        function getNameField() {
            return rowElement.find('#' + nameFieldId);
        }

        function getClientField() {
            return rowElement.find('#' + clientFieldId);
        }

        function getClientLocationField() {
            return rowElement.find('#' + locationFieldId);
        }

        function getActiveField() {
            return rowElement.find('#' + activeFieldId);
        }

        function getChargeableField() {
            return rowElement.find('#' + chargeableFieldId);
        }

        function getCompanyServiceField() {
            return rowElement.find('#' + companyServiceFieldId);
        }

        function setValues() {

            getNameField().val(timeCode.name);
            getActiveField().prop("checked", timeCode.active === true);
            getChargeableField().prop("checked", timeCode.chargeable === true);
            //getClientLocationField().data('value', );
            locationNumber = timeCode.clientLocation.number;

            let $clientField = getClientField();

            $clientField.val(timeCode.client.number);
            $clientField.change();

            setCompanyServiceFieldVal();

        }
        
        function setCompanyServiceFieldVal(){
            
            getCompanyServiceField().val(timeCode.companyService.code);
        }
        
        this.getTimeCode = function () {

            if (editMode === true) {

                let editTimeCode = {client: {}, clientLocation: {}, companyService: {}};

                editTimeCode.uid = timeCode.uid;
                editTimeCode.name = getNameField().val().trim();
                editTimeCode.client.number = getClientField().val();
                editTimeCode.clientLocation.number = getClientLocationField().val();
                editTimeCode.active = getActiveField().prop("checked");
                editTimeCode.chargeable = getChargeableField().prop("checked");
                editTimeCode.companyService.code = getCompanyServiceField().val();

                return editTimeCode;
            }

            return timeCode;
        };

        this.setRow = function (rowControl, row) {
            rowElement = rowControl;
            timeCode = row;

            setEditColumnElements(rowControl);

            rowControl.find(":input").change(function (evt) {

                if (typeof onFieldValueChanged === 'function') {
                    onFieldValueChanged(evt);
                }
            });

            setClientDropdown(rowControl);
            setCompanyServiceDropdown(rowControl);

            editMode = true;

            setValues();
        };

        this.registerOnFieldValueChanged = function (valueChanged) {
            onFieldValueChanged = valueChanged;
        };

        this.isClientControl = function (control) {
            return control.attr('id') === clientFieldId;
        };

        this.selectClient = function () {
            let $clientField = getClientControl(rowElement);

            $clientField.val(timeCode.client.number);
            $clientField.change();
        };
        
        this.selectCompanyService = function(){
            setCompanyServiceFieldVal();
        };
        
        this.registerLoadClientOptions = function (loadOptions) {

            loadClientOptions = loadOptions;
        };

        this.registerLoadCompanyServiceOptions = function (loadOptions) {

            loadCompanyServiceOptions = loadOptions;
        };

        this.getLocationControl = function () {
            return getLocationControl(rowElement);
        };

        this.setEditMode = function (value) {
            editMode = value;
        };

        this.getEditMode = function () {
            return editMode;
        };

        this.reset = function () {
            timeCode = {};
            editMode = false;
        };

        this.selectClientLocation = function () {

            getClientLocationField().val(locationNumber);

            //Reset the number when client changed but to set first time
            locationNumber = "";
        };
    }

    return TimeCodeRowEdit;
}());
