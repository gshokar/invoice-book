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

        $aatl_ib.ApiService.get("employee/find",
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

    save: function (employee, callback) {

        let err = $aatl_ib.EmployeeService.validate(employee);

        if (err.messages.length === 0) {
            $aatl_ib.ApiService.post("employee",
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

            if (typeof employee.lastName !== 'string' || employee.lastName.trim().length === 0) {
                err.messages.push("Please enter employee last name.");
            }

            if (typeof employee.firstName !== 'string' || employee.firstName.trim().length === 0) {
                err.messages.push("Please enter employee first name.");
            }

            if (typeof employee.birthDate !== 'string' || employee.birthDate.trim().length === 0) {
                err.messages.push("Please enter employee date of birth.");
            }

            if (employee.address && typeof employee.address.address1 !== 'string' || employee.address.address1.trim().length === 0) {
                err.messages.push("Please enter address line 1.");
            }

            if (employee.address && typeof employee.address.city !== 'string' || employee.address.city.trim().length === 0) {
                err.messages.push("Please enter city.");
            }

            if (employee.address && typeof employee.address.province !== 'string' || employee.address.province.trim().length === 0) {
                err.messages.push("Please enter province.");
            }

            if (employee.address && typeof employee.address.postalCode !== 'string' || employee.address.postalCode.trim().length === 0) {
                err.messages.push("Please enter postal code.");
            }
        }

        return err;
    }
};

