/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-25  GShokar         Created
 * =============================================================================
 */


$aatl_ib.utils = {

    createUniqueId: function () {

        function getRandomInt(min, max) {
            return Math.floor(Math.random() * (max - min + 1)) + min;
        }

        function getRandomChar() {
            return String.fromCharCode(getRandomInt(65, 90));
        }
        let id = new Date().getTime().toString();
        let pos;

        for (var i = 0; i < 6; i++) {
            pos = getRandomInt(1, 12);
            id = id.substr(0, pos) + getRandomChar() + id.substr(pos);
        }

        return id;
    },

    currentDate: function () {
        var today = new Date();

        return new Date(today.getFullYear(), today.getMonth(), today.getDate());
    },

    isFunction: function (obj) {

        return obj !== undefined && obj !== null && typeof obj === "function";
    },

    isStringEmpty: function(value){
        // undefinded or null
        return typeof value !== 'string' || value.trim().length === 0;
    },
            
    replaceElementId: function (html, oldId, newId) {

        return $aatl_ib.utils.replaceElementAttribute(html, 'id', oldId, newId);
    },

    replaceElementAttribute: function (html, attribute, value, newValue) {
        
        if(attribute === 'data-target'){
            value = '#' + value;
            newValue = '#' + newValue;
        }
        
        let currentAttribute = attribute + '="' + value + '" ';
        let newAttribute = attribute + '="' + newValue + '" ';

        if (html.indexOf(currentAttribute) === -1) {
            currentAttribute = attribute + '="' + value + '">';
            newAttribute = attribute + '="' + newValue + '">';
        }

        return html.replace(currentAttribute, newAttribute);
    },

    replaceElementAttributeFor: function (html, value, newValue) {

        return $aatl_ib.utils.replaceElementAttribute(html, 'for', value, newValue);
    },

    addDropdownOptions: function (control, options) {
        control.empty();

        $.each(options, function (index, option) {
            control.append($('<option></option>').val(option.code).text(option.name));
        });
    },
    
    getEmployeeName: function(employee){
        return employee.firstName + ' ' + employee.lastName;
    }
};