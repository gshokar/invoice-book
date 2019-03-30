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

    isStringEmpty: function (value) {
        // undefinded or null
        return typeof value !== 'string' || value.trim().length === 0;
    },

    replaceElementId: function (html, oldId, newId) {

        return $aatl_ib.utils.replaceElementAttribute(html, 'id', oldId, newId);
    },

    replaceElementAttribute: function (html, attribute, value, newValue) {

        if (attribute === 'data-target') {
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

        options.forEach(function (option) {
            control.append($('<option></option>').val(option.code).text(option.name));
        });
    },

    getEmployeeName: function (employee) {
        return employee.firstName + ' ' + employee.lastName;
    },

    calcHours: function (time1, time2) {
        var hours = 0;
        var start = time1.split(":");
        var end = time2.split(":");

        if (start.length === 2 && end.length === 2) {
            try {
                var startDate = new Date(0, 0, 0, start[0], start[1], 0);
                var endDate = new Date(0, 0, 0, end[0], end[1], 0);
                var diff = endDate.getTime() - startDate.getTime();
                var hours = diff / 1000 / 60 / 60;

                // Round to 2 decimals
                hours = Math.round(hours * 100) / 100;
            } catch (ex) {
                console.log("time1 or time2 is invalid");
            }
        }

        return hours;
    },

    months: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],

    displayDateFormat: function (date) {

        let displayVal = "";
        let displayDate = undefined;

        if (typeof date === "string") {
            try {
                displayDate = $aatl_ib.utils.parseDate(date);
            } catch (ex) {

            }

        } else if (date instanceof Date) {
            displayDate = date;
        }

        if (displayDate instanceof Date) {
            displayVal = ("0" + displayDate.getDate()).slice(-2) + "-"
                    + $aatl_ib.utils.months[displayDate.getMonth()] + "-"
                    + displayDate.getFullYear();
        }
        return displayVal;
    },

    parseDate: function (date) {
        let dateVal = undefined;

        if (typeof date === "string" && date.length === 10) {

            let year = date.slice(0, 4);
            let month = date.slice(5, 7);
            let day = date.slice(8, 10);

            if (!isNaN(year) && !isNaN(month) && !isNaN(day)) {
                dateVal = new Date(parseInt(year), parseInt(month) - 1, parseInt(day));
            }
        }
        return dateVal;
    },

    parseTime: function (time) {
        //Assumtions time value hh:mm or hh:mm:ss
        let timeVal = undefined;

        if (typeof time === "string"
                && (time.length === 5 || time.length === 8)) {

            let hours = time.slice(0, 2);
            let mintues = time.slice(3, 5);

            if (!isNaN(hours) && !isNaN(mintues)) {
                timeVal = new Date();

                timeVal.setHours(parseInt(hours));
                timeVal.setMinutes(parseInt(mintues));
                timeVal.setSeconds(0);

                if (time.length === 8) {
                    let seconds = time.slice(6, 8);

                    if (!isNaN(seconds)) {
                        timeVal.setSeconds(parseInt(seconds));
                    } else {
                        timeVal = undefined;
                    }
                }
            }
        }
        return timeVal;
    },

    displayTimeFormat: function (time) {

        let displayVal = "";
        let displayTime = undefined;

        if (typeof time === "string") {
            try {
                displayTime = $aatl_ib.utils.parseTime(time);
            } catch (ex) {

            }

        } else if (time instanceof Date) {
            displayTime = time;
        }

        if (displayTime instanceof Date) {
            let marker = "AM";
            let hours = displayTime.getHours();

            if (hours > 11) {
                marker = 'PM';

                if (hours > 12) {
                    hours = hours - 12;
                }
            }
            displayVal = ("0" + hours).slice(-2) + ":"
                    + ("0" + displayTime.getMinutes()).slice(-2) + " "
                    + marker;
        }
        return displayVal;
    },
    isDate: function (value) {

        let dateValue = $aatl_ib.utils.parseDate(value);

        return dateValue instanceof Date;
    }
};