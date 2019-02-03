/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-23  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.TimeCodeService = {

    find: function (criteria, callback) {

        $aatl_ib.ApiService.get("time-codes/find",
                criteria,
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get time code results: " + err]});

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

    save: function (timeCode, callback) {

        let err = $aatl_ib.TimeCodeService.validate(timeCode);

        if (err.messages.length === 0) {
            $aatl_ib.ApiService.put("time-codes",
                    timeCode,
                    function (res, serverErr) {
                        if (serverErr) {

                            err.messages.push("Failed to save the time code: " + serverErr);
                            callback(timeCode, err);

                        } else if (res.status === "failure") {

                            err.messages.push(res.message);
                            callback(timeCode, err);

                        } else if (res.status === "success") {

                            if (Array.isArray(res.warningMessages) && res.warningMessages.length > 0) {
                                err.messages = res.warningMessages;
                            }

                            callback(JSON.parse(res.data), err);
                        } else {
                            callback(timeCode, "Invalid response from server");
                        }
                    });

            return;
        }

        callback(timeCode, err);
    },
    list: function (callback) {

        $aatl_ib.ApiService.get("time-codes",
                {},
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get time code list: " + err]});

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
    validate: function (timeCode) {

        let err = {messages: []};

        if (timeCode) {

            if ($aatl_ib.utils.isStringEmpty(timeCode.name)) {
                err.messages.push("Please enter time code name.");
            }

        }

        return err;
    }
};

