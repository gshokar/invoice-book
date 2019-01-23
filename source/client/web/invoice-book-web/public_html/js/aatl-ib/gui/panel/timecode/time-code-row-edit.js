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
        let rowElement = undefined;

        let onFieldValueChanged = undefined;
        let loadClientOptions = undefined;

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
                        $element.append(getClientElement());
                        break;
                    case 3:
                        $element.append(getLocationElement());
                        break;
                    case 4:
                        $element.append(getActiveElement());
                        break;
                    case 5:
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
        
        function setClientDropdown(rowControl) {

            if (typeof loadClientOptions === 'function') {
                loadClientOptions(getClientControl(rowControl));
            }
        }

        this.getTimeCode = function () {
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

            //setValues();
        };

        this.registerOnFieldValueChanged = function (valueChanged) {
            onFieldValueChanged = valueChanged;
        };

        this.isClientControl = function (control) {
            return control.attr('id') === clientFieldId;
        };

        this.selectClient = function () {
            getClientControl(rowElement).val(timeCode.client.number);
        };

        this.registerLoadClientOptions = function (loadOptions) {

            loadClientOptions = loadOptions;
        };

        this.getLocationControl = function () {
            return getLocationControl(rowElement);
        };
    }

    return TimeCodeRowEdit;
}());
