/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-26  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.ClientService = {

    find: function (criteria, callback) {

        $aatl_ib.ApiService.get("clients/find",
                criteria,
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get client search results: " + err]});

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
    list: function (callback) {

        $aatl_ib.ApiService.get("clients",
        {},
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get client list: " + err]});

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
            $aatl_ib.ApiService.get("clients/" + number,
                    {},
                    function (res, err) {
                        if (err) {
                            callback(null, {messages: ["Faild to get client detail: " + err]});

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

    save: function (client, callback) {

        let err = $aatl_ib.ClientService.validate(client);

        if (err.messages.length === 0) {
            $aatl_ib.ApiService.put("clients",
                    client,
                    function (res, serverErr) {
                        if (serverErr) {

                            err.messages.push("Failed to save the client: " + serverErr);
                            callback(client, err);

                        } else if (res.status === "failure") {

                            err.messages.push(res.message);
                            callback(client, err);

                        } else if (res.status === "success") {

                            if (Array.isArray(res.warningMessages) && res.warningMessages.length > 0) {
                                err.messages = res.warningMessages;
                            }

                            callback(JSON.parse(res.data), err);
                        } else {
                            callback(client, "Invalid response from server");
                        }
                    });

            return;
        }

        callback(client, err);
    },

    validate: function (client) {

        let err = {messages: []};

        if (client) {

            if ($aatl_ib.utils.isStringEmpty(client.name)) {
                err.messages.push("Please enter client name.");
            }

            if (client.address && $aatl_ib.utils.isStringEmpty(client.address.address1)) {
                err.messages.push("Please enter address line 1.");
            }

            if (client.address && $aatl_ib.utils.isStringEmpty(client.address.city)) {
                err.messages.push("Please enter city.");
            }

            if (client.address && $aatl_ib.utils.isStringEmpty(client.address.province)) {
                err.messages.push("Please enter province.");
            }

            if (client.address && $aatl_ib.utils.isStringEmpty(client.address.postalCode)) {
                err.messages.push("Please enter postal code.");
            }
        }

        return err;
    }
};


