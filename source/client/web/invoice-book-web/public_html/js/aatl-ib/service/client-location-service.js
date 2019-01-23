/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-07  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.ClientLocationService = {

    find: function (criteria, callback) {

        $aatl_ib.ApiService.get("client/location/find",
                criteria,
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get client location search results: " + err]});

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
                name: "",
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
            $aatl_ib.ApiService.get("client/location/" + number,
                    {},
                    function (res, err) {
                        if (err) {
                            callback(null, {messages: ["Faild to get client location detail: " + err]});

                        } else if (res.status === "failure") {
                            callback(null, {messages: [res.message]});
                        } else if (res.status === "success") {
                            let client = JSON.parse(res.data);
                            callback(client);
                        } else {
                            callback(null, {messages: ["Invalid response from server"]});
                        }
                    });
        }
    },

    save: function (location, callback) {

        let err = $aatl_ib.ClientLocationService.validate(location);

        if (err.messages.length === 0) {

            $aatl_ib.ApiService.put("client/location",
                    location,
                    function (res, serverErr) {
                        if (serverErr) {

                            err.messages.push("Failed to save the client location: " + serverErr);
                            callback(location, err);

                        } else if (res.status === "failure") {

                            err.messages.push(res.message);
                            callback(location, err);

                        } else if (res.status === "success") {

                            if (Array.isArray(res.warningMessages) && res.warningMessages.length > 0) {
                                err.messages = res.warningMessages;
                            }

                            callback(JSON.parse(res.data), err);
                        } else {
                            callback(location, "Invalid response from server");
                        }
                    });

            return;
        }

        callback(location, err);
    },
    list: function (number, callback) {

        $aatl_ib.ApiService.get("client/location/list/" + number,
                {},
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get client locations: " + err]});

                    } else if (res.status === "failure") {
                        callback(null, {messages: [res.message]});
                    } else if (res.status === "success") {
                        let client = JSON.parse(res.data);
                        callback(client);
                    } else {
                        callback(null, {messages: ["Invalid response from server"]});
                    }
                });

    },

    validate: function (location) {

        let err = {messages: []};

        if (location) {

            if ($aatl_ib.utils.isStringEmpty(location.name)) {
                err.messages.push("Please enter location name.");
            }

            if (location.address && $aatl_ib.utils.isStringEmpty(location.address.address1)) {
                err.messages.push("Please enter address line 1.");
            }

            if (location.address && $aatl_ib.utils.isStringEmpty(location.address.city)) {
                err.messages.push("Please enter city.");
            }

            if (location.address && $aatl_ib.utils.isStringEmpty(location.address.province)) {
                err.messages.push("Please enter province.");
            }

            if (location.address && $aatl_ib.utils.isStringEmpty(location.address.postalCode)) {
                err.messages.push("Please enter postal code.");
            }
        }

        return err;
    }
};
