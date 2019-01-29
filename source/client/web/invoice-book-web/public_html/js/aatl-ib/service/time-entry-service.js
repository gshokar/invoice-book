/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-28  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.TimeEntryService = {

    find: function (criteria, callback) {

        $aatl_ib.ApiService.get("time-entries/find",
                criteria,
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get time entries: " + err]});

                    } else if (res.status === "failure") {
                        callback(null, {messages: [res.message]});
                    } else if (res.status === "success") {
                        let list = JSON.parse(res.data);
                        callback(list);
                    } else {
                        callback(null, {messages: ["Invalid response from server"]});
                    }
                });
    },

    get: function (number, callback) {

        if (number === undefined || number === null) {
            callback({
                number: "",
                lastName: "",
                firstName: "",
                birthDate: "",
                address: {
                    address1: "",
                    address2: "",
                    city: "",
                    province: $aatl_ib.LookupService.defaultProvince.code,
                    postalCode: ""
                },
                contact: {
                    phone: "",
                    email: ""
                }
            });
        } else {
            $aatl_ib.ApiService.get("employee/" + number,
                    {},
                    function (res, err) {
                        if (err) {
                            callback(null, {messages: ["Faild to get employee detail: " + err]});

                        } else if (res.status === "failure") {
                            callback(null, {messages: [res.message]});
                        } else if (res.status === "success") {
                            let employee = JSON.parse(res.data);
                            callback(employee);
                        } else {
                            callback(null, {messages: ["Invalid response from server"]});
                        }
                    });
        }
    },

    save: function (timeEntry, callback) {

        let err = $aatl_ib.TimeEntryService.validate(timeEntry);

        if (err.messages.length === 0) {
            $aatl_ib.ApiService.put("time-entries",
                    timeEntry,
                    function (res, serverErr) {
                        if (serverErr) {

                            err.messages.push("Failed to save the time entry: " + serverErr);
                            callback(timeEntry, err);

                        } else if (res.status === "failure") {

                            err.messages.push(res.message);
                            callback(timeEntry, err);

                        } else if (res.status === "success") {

                            if (Array.isArray(res.warningMessages) && res.warningMessages.length > 0) {
                                err.messages = res.warningMessages;
                            }

                            callback(JSON.parse(res.data), err);
                        } else {
                            callback(timeEntry, "Invalid response from server");
                        }
                    });

            return;
        }

        callback(timeEntry, err);
    },
    list: function (callback) {

        $aatl_ib.ApiService.get("time-entries",
                {},
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get time entry list: " + err]});

                    } else if (res.status === "failure") {
                        callback(null, {messages: [res.message]});
                    } else if (res.status === "success") {
                        let list = JSON.parse(res.data);
                        callback(list);
                    } else {
                        callback(null, {messages: ["Invalid response from server"]});
                    }
                });
    },
    validate: function (timeEntry) {

        let err = {messages: []};

        if (timeEntry) {

            if ($aatl_ib.utils.isStringEmpty(timeEntry.date)) {
                err.messages.push("Please select enter date.");
            }
            if ($aatl_ib.utils.isStringEmpty(timeEntry.timeCode.uid)) {
                err.messages.push("Please select time code.");
            }
            if ($aatl_ib.utils.isStringEmpty(timeEntry.startTime)) {
                err.messages.push("Please enter start time.");
            }
            if ($aatl_ib.utils.isStringEmpty(timeEntry.endTime)) {
                err.messages.push("Please enter end time.");
            }

        }

        return err;
    }
};


