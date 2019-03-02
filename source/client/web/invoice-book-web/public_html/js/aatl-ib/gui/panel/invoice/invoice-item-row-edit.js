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
        let defaultRowValues = {};
        
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

            let element = '<input type="text" class="form-control" id="' + amountElementId + '">';

            return element;
        }

        function getColumnControl(id) {
            return rowElement.find('#' + id);
        }
        
        function getItemTypeElementControl() {
            return getColumnControl(itemTypeElementId);
        }
        
        function getDescriptionElementControl(){
            return getColumnControl(descriptionElementId);
        }
        
        function getQuantityElementControl(){
            return getColumnControl(quantityElementId);
        }
        
        function getUnitElementControl(){
            return getColumnControl(unitElementId);
        }
        
        function getRateElementControl(){
            return getColumnControl(rateElementId);
        }
        
        function getAmountElementControl(){
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
        
        function setDefaultRowValues(){
            
            defaultRowValues.description = item.description;
            defaultRowValues.quantity = item.quantity;
            
            if(item.salesItem !== undefined 
                    && item.salesItem !== null){
                        
                        if(item.salesItem.unit !== undefined
                    && item.salesItem.unit !== null){
                
                getUnitElementControl().val(item.salesItem.unit.name);
            }
            }
                    
            
            getRateElementControl().val(item.rate.toFixed(2));
            getAmountElementControl().val(item.rate.toFixed(2));
        }
        this.getItem = function () {
            return item;
        };

        this.setRow = function (rowControl, row) {
            rowElement = rowControl;
            item = row;

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
        
        this.getEditMode = function(){
            return editMode;
        };
        
        this.registerLoadItemTypeOptions = function(loadOptions){
            loadItemTypeOptions = loadOptions;
        };
        
        this.selectItemType = function(){
            
        };
        
        
    }
    return InvoiceItemRowEdit;
}());