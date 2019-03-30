/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-24  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.InvoiceItemRowEdit = (function () {
    function InvoiceItemRowEdit() {
        let item = {};
        let rowElement = undefined;
        let editMode = false;
        let settingRowValues = false;
        let salesItems = [];

        let itemTypeElementId = "";
        let itemElementId = "";
        let descriptionElementId = "";
        let quantityElementId = "";
        let unitElementId = "";
        let rateElementId = "";
        let amountElementId = "";

        let onFieldValueChanged = undefined;
        let loadItemTypeOptions = undefined;

        function getItemTypeElement() {
            itemTypeElementId = $aatl_ib.utils.createUniqueId();

            let element = '<select class="form-control" id="' + itemTypeElementId + '"><option selected></option></select>';

            return element;
        }

        function getItemElement() {
            itemElementId = $aatl_ib.utils.createUniqueId();

            let element = '<select class="form-control" id="' + itemElementId + '"><option selected></option></select>';

            return element;
        }

        function getDescriptionElement() {
            descriptionElementId = $aatl_ib.utils.createUniqueId();

            let element = '<input type="text" class="form-control" id="' + descriptionElementId + '">';

            return element;
        }

        function getQuantityElement() {
            quantityElementId = $aatl_ib.utils.createUniqueId();

            let element = '<input type="text" class="form-control" id="' + quantityElementId + '">';

            return element;
        }

        function getUnitElement() {
            unitElementId = $aatl_ib.utils.createUniqueId();

            let element = '<input type="text" class="form-control" id="' + unitElementId + '" disabled>';

            return element;
        }

        function getRateElement() {
            rateElementId = $aatl_ib.utils.createUniqueId();

            let element = '<input type="text" class="form-control" id="' + rateElementId + '">';

            return element;
        }

        function getAmountElement() {
            amountElementId = $aatl_ib.utils.createUniqueId();

            let element = '<input type="text" class="form-control" id="' + amountElementId + '" disabled>';

            return element;
        }

        function getColumnControl(id) {
            return rowElement.find('#' + id);
        }

        function getItemTypeElementControl() {
            return getColumnControl(itemTypeElementId);
        }

        function getItemElementControl() {
            return getColumnControl(itemElementId);
        }

        function getDescriptionElementControl() {
            return getColumnControl(descriptionElementId);
        }

        function getQuantityElementControl() {
            return getColumnControl(quantityElementId);
        }

        function getUnitElementControl() {
            return getColumnControl(unitElementId);
        }

        function getRateElementControl() {
            return getColumnControl(rateElementId);
        }

        function getAmountElementControl() {
            return getColumnControl(amountElementId);
        }

        function setEditColumnElements(rowControl) {
            let columns = rowControl.children();

            for (var i = 1; i < columns.length; i++) {

                let $element = $(columns[i]);

                $element.empty();

                switch (i) {
                    case 1:
                        $element.append(getItemTypeElement());
                        break;
                    case 2:
                        $element.append(getItemElement());
                        break;
                    case 3:
                        $element.append(getDescriptionElement());
                        break;
                    case 4:
                        $element.append(getQuantityElement());
                        break;
                    case 5:
                        $element.append(getUnitElement());
                        break;
                    case 6:
                        $element.append(getRateElement());
                        break;
                    case 7:
                        $element.append(getAmountElement());
                        break;
                }

            }
        }
        function getSelectedSalesItem() {

            let code = getItemElementControl().val();

            let salesItem = salesItems.find((si) => {
                return si.code === code;
            });
            
            if(!salesItem){
                salesItem = {};
            }
            
            return salesItem;
        }
        function setDefaultRowValues() {

            defaultRowValues.description = item.description;
            defaultRowValues.quantity = item.quantity;

            if (item.salesItem !== undefined
                    && item.salesItem !== null) {

                if (item.salesItem.unit !== undefined
                        && item.salesItem.unit !== null) {

                    getUnitElementControl().val(item.salesItem.unit.name);
                }
            }


            getRateElementControl().val(item.rate.toFixed(2));
            getAmountElementControl().val(item.rate.toFixed(2));
        }
        this.getItem = function () {

            if (editMode === true) {

                let editItem = {lineNumber: item.lineNumber,
                    uid: item.uid,
                    description: getDescriptionElementControl().val(),
                    quantity: getQuantityElementControl().val(),
                    rate: getRateElementControl().val(),
                    amount: getAmountElementControl(),
                    taxAmount: 0.00,
                    totalAmount: 0.00,
                    taxes: [],
                    salesItem: getSelectedSalesItem()
                };

                return editItem;
            }
            return item;
        };

        this.setRow = function (rowControl, row) {
            rowElement = rowControl;
            item = row;
            settingRowValues = true;

            setEditColumnElements(rowControl);

            rowControl.find(":input").change(function (evt) {

                if (typeof onFieldValueChanged === 'function') {
                    onFieldValueChanged(evt);
                }
            });

            if (typeof loadItemTypeOptions === 'function') {
                loadItemTypeOptions(getItemTypeElementControl());
            }

            editMode = true;

            //setValues();
        };

        this.getEditMode = function () {
            return editMode;
        };

        this.registerLoadItemTypeOptions = function (loadOptions) {
            loadItemTypeOptions = loadOptions;
        };

        this.registerOnFieldValueChanged = function (valueChanged) {
            onFieldValueChanged = valueChanged;
        };

        this.selectItemType = function () {

            let $itemTypeField = getItemTypeElementControl();

            if (item.salesItem
                    && item.salesItem.itemType
                    && item.salesItem.itemType.uid
                    && item.salesItem.itemType.uid.length > 0) {

                $itemTypeField.val(item.salesItem.itemType.uid);
            }

            $itemTypeField.change();
        };

        this.selectItem = function () {

            let $itemField = getItemElementControl();

            if (settingRowValues === true
                    && item.salesItem
                    && item.salesItem.code
                    && item.salesItem.code.length > 0) {

                $itemField.val(item.salesItem.code);
            } else {
                $itemField.val("");
            }

            $itemField.change();
        };

        this.isItemTypeControl = function (control) {
            return control.attr('id') === itemTypeElementId;
        };

        this.isItemControl = function (control) {
            return control.attr('id') === itemElementId;
        };

        this.getItemControl = function () {
            return getItemElementControl();
        };

        this.setSalesItems = function (list) {
            salesItems = list;
        };

        this.itemChanged = function (value) {

            if (value === "") {
                getDescriptionElementControl().val("");
                getQuantityElementControl().val(0.00);
                getUnitElementControl().val("");
                getRateElementControl().val(0.00);
                getAmountElementControl().val(0.00);
            } else if (settingRowValues) {
                getDescriptionElementControl().val(item.description);
                getQuantityElementControl().val(item.quantity);

                if (item.salesItem !== undefined
                        && item.salesItem !== null) {

                    if (item.salesItem.unit !== undefined
                            && item.salesItem.unit !== null) {

                        getUnitElementControl().val(item.salesItem.unit.name);
                    }
                }

                getRateElementControl().val(item.rate.toFixed(2));
                getAmountElementControl().val(item.amount.toFixed(2));

            } else {
                let salesItem = salesItems.find((si) => {
                    return si.code === value;
                });

                if (salesItem) {
                    getDescriptionElementControl().val("");
                    getQuantityElementControl().val(0.00);


                    if (salesItem.unit !== undefined
                            && salesItem.unit !== null) {

                        getUnitElementControl().val(salesItem.unit.name);
                    }

                    getRateElementControl().val(salesItem.rate.toFixed(2));
                    getAmountElementControl().val("0.00");
                }
            }

            settingRowValues = false;

        };

        this.isCalcAmount = function (control) {
            let id = control.attr('id');
            return id === quantityElementId || id === rateElementId;
        };

        this.calcAmount = function () {
            let quantity = parseFloat(getQuantityElementControl().val());
            let rate = parseFloat(getRateElementControl().val());

            if (!isNaN(quantity) && !isNaN(rate)) {
                let amount = quantity * rate;

                getAmountElementControl().val(amount.toFixed(2));
            }
        };
        
        this.reset = function () {
            item = {};
            editMode = false;
        };
    }
    return InvoiceItemRowEdit;
}());