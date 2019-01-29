/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-25  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.EmployeeService = {

    find: function (criteria, callback) {

        $aatl_ib.ApiService.get("employees/find",
                criteria,
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get employee search results: " + err]});

                    } else if (res.status === "failure") {
                        callback(null, {messages: [res.message]});
                    } else if (res.status === "success") {
                        let employees = JSON.parse(res.data);
                        callback(employees);
                    } else {
                        callback(null, {messages: ["Invalid response from server"]});
                    }
                });
    },
    list: function (callback) {

        $aatl_ib.ApiService.get("employees",
        {},
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get employee list: " + err]});

                    } else if (res.status === "failure") {
                        callback(null, {messages: [res.message]});
                    } else if (res.status === "success") {
                        let clients = JSON.parse(res.data);
                        callback(clients);
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
            $aatl_ib.ApiService.get("employees/" + number,
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

    save: function (employee, callback) {

        let err = $aatl_ib.EmployeeService.validate(employee);

        if (err.messages.length === 0) {
            $aatl_ib.ApiService.put("employees",
                    employee,
                    function (res, serverErr) {
                        if (serverErr) {

                            err.messages.push("Failed to save the employee: " + serverErr);
                            callback(employee, err);

                        } else if (res.status === "failure") {

                            err.messages.push(res.message);
                            callback(employee, err);

                        } else if (res.status === "success") {

                            if (Array.isArray(res.warningMessages) && res.warningMessages.length > 0) {
                                err.messages = res.warningMessages;
                            }

                            callback(JSON.parse(res.data), err);
                        } else {
                            callback(employee, "Invalid response from server");
                        }
                    });

            return;
        }

        callback(employee, err);
    },

    validate: function (employee) {

        let err = {messages: []};

        if (employee) {

            if ($aatl_ib.utils.isStringEmpty(employee.lastName)) {
                err.messages.push("Please enter employee last name.");
            }

            if ($aatl_ib.utils.isStringEmpty(employee.firstName)) {
                err.messages.push("Please enter employee first name.");
            }

            if ($aatl_ib.utils.isStringEmpty(employee.birthDate)) {
                err.messages.push("Please enter employee date of birth.");
            }

            if (employee.address && $aatl_ib.utils.isStringEmpty(employee.address.address1)) {
                err.messages.push("Please enter address line 1.");
            }

            if (employee.address && $aatl_ib.utils.isStringEmpty(employee.address.city)) {
                err.messages.push("Please enter city.");
            }

            if (employee.address && $aatl_ib.utils.isStringEmpty(employee.address.province)) {
                err.messages.push("Please enter province.");
            }

            if (employee.address && $aatl_ib.utils.isStringEmpty(employee.address.postalCode)) {
                err.messages.push("Please enter postal code.");
            }
        }

        return err;
    }
};

