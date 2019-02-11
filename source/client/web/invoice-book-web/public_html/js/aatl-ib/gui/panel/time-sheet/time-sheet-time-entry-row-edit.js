/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-27  GShokar         Created
 * =============================================================================
 */
"use strict";

$aatl_ib.gui.TimeSheetTimeEntryRowEdit = (function () {
    function TimeSheetTimeEntryRowEdit() {
        let timeEntry = {};

        let dateFieldId = "";
        let timeCodeFieldId = "";
        let startTimeFieldId = "";
        let endTimeFieldId = "";
        let hoursFieldId = "";
        let approvedFieldId = "";

        let rowElement = undefined;
        let editMode = false;
        let timeCodeClientNumber = "";

        let onFieldValueChanged = undefined;
        let loadTimeCodeOptions = undefined;

        function getDateElement() {

            dateFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<input type="text" class="form-control" id="' + dateFieldId + '" placeholder="dd-mmm-yyyy" data-provide="datepicker">';

            return row;
        }

        function getTimeCodeElement() {

            timeCodeFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<select class="form-control" id="' + timeCodeFieldId + '"><option selected></option></select>';

            return row;
        }

        function getStartTimeElement() {

            startTimeFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<input type="time" class="form-control" id="' + startTimeFieldId + '">';

            return row;
        }

        function getEndTimeElement() {

            endTimeFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<input type="time" class="form-control" id="' + endTimeFieldId + '">';

            return row;
        }

        function getHoursElement() {

            hoursFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<input type="text" class="form-control" id="' + hoursFieldId + '" disabled>';

            return row;
        }

        function getApprovedElement() {

            approvedFieldId = $aatl_ib.utils.createUniqueId();

            let row = '<input type="checkbox" class="form-control" id="' + approvedFieldId + '">';

            return row;
        }

        function setEditColumnElements(rowControl) {
            let columns = rowControl.children();

            for (var i = 1; i < columns.length; i++) {

                let $element = $(columns[i]);

                $element.empty();

                switch (i) {
                    case 1:
                        $element.append(getDateElement());
                        break;
                    case 2:
                        $element.append(getTimeCodeElement());
                        break;
                    case 3:
                        $element.append(getStartTimeElement());
                        break;
                    case 4:
                        $element.append(getEndTimeElement());
                        break;
                    case 5:
                        $element.append(getHoursElement());
                        break;
                    case 6:
                        $element.append(getApprovedElement());
                        break;
                }

            }
        }

        function getTimeCodeControl(rowControl) {
            return rowControl.find('#' + timeCodeFieldId);
        }

        function setTimeCodeDropdown(rowControl) {

            if (typeof loadTimeCodeOptions === 'function') {
                loadTimeCodeOptions(getTimeCodeControl(rowControl), timeCodeClientNumber);
            }
        }

        function getDateField() {
            return rowElement.find('#' + dateFieldId);
        }

        function getTimeCodeField() {
            return rowElement.find('#' + timeCodeFieldId);
        }

        function getStartTimeField() {
            return rowElement.find('#' + startTimeFieldId);
        }

        function getEndTimeField() {
            return rowElement.find('#' + endTimeFieldId);
        }

        function getHoursField() {
            return rowElement.find('#' + hoursFieldId);
        }

        function getApprovedField() {
            return rowElement.find('#' + approvedFieldId);
        }

        function setValues() {

            let date = $aatl_ib.utils.parseDate(timeEntry.date);

            if (date instanceof Date) {
                getDateField().datepicker('setDate', date);
            }
            //getTimeCodeField().val(timeEntry.timeCode.uid);
            getStartTimeField().val(timeEntry.startTime);
            getEndTimeField().val(timeEntry.endTime);
            getHoursField().val(timeEntry.hours.toFixed(2));
            getApprovedField().prop("checked", timeEntry.approved === true);
        }

        function setDatePickerControls(startDate) {
            let endDate = new Date(startDate);

            // Last day of the month
            endDate.setMonth(endDate.getMonth() + 1, 0);

            getDateField().datepicker({
                autoclose: true,
                format: "dd-M-yyyy",
                startDate: startDate,
                endDate: endDate
            });
        }

        this.getTimeEntry = function () {

            if (editMode === true) {

                let editTimeEntry = {timeCode: {}, employee: {}};

                editTimeEntry.uid = timeEntry.uid;
                editTimeEntry.employee.number = timeEntry.employee.number

                let date = getDateField().datepicker('getDate');

                if (date instanceof Date) {
                    // this will be yyyy-MM-dd
                    editTimeEntry.date = date.toISOString().slice(0, 10);
                } else {
                    editTimeEntry.date = "";
                }
                editTimeEntry.timeCode.uid = getTimeCodeField().val();
                editTimeEntry.startTime = getStartTimeField().val();
                editTimeEntry.endTime = getEndTimeField().val();
                editTimeEntry.approved = getApprovedField().prop("checked");
                editTimeEntry.charged = timeEntry.charged;
                
                return editTimeEntry;
            }

            return timeEntry;
        };

        this.setRow = function (rowControl, row, startDate, clientNumber) {
            rowElement = rowControl;
            timeEntry = row;
            timeCodeClientNumber = clientNumber;

            setEditColumnElements(rowControl);

            rowControl.find(":input").change(function (evt) {

                if (typeof onFieldValueChanged === 'function') {
                    onFieldValueChanged(evt);
                }
            });

            setDatePickerControls(startDate);

            setTimeCodeDropdown(rowControl);

            editMode = true;

            setValues();
        };

        this.registerOnFieldValueChanged = function (valueChanged) {
            onFieldValueChanged = valueChanged;
        };

        this.selectTimeCode = function () {

            getTimeCodeControl(rowElement).val(timeEntry.timeCode.uid);

        };

        this.registerLoadTimeCodeOptions = function (loadOptions) {

            loadTimeCodeOptions = loadOptions;
        };

        this.setEditMode = function (value) {
            editMode = value;
        };

        this.getEditMode = function () {
            return editMode;
        };

        this.reset = function () {
            timeEntry = {};
            editMode = false;
        };

        this.calcHours = function (id) {

            if (id === startTimeFieldId || id === endTimeFieldId) {

                getHoursField().val($aatl_ib.utils.calcHours(getStartTimeField().val(), getEndTimeField().val()));
            }
        };
    }

    return TimeSheetTimeEntryRowEdit;
}());


