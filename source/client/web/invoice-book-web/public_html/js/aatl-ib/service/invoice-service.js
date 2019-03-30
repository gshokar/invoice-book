/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Mar-21  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.InvoiceService = {
    get: function (number, callback) {

        if (number === undefined || number === null) {
            callback({
                number: "",
                date: "",
                amount: 0.00,
                taxAmount: 0.00,
                totalAmount: 0.00,
                paidAmount: 0.00,
                client: {
                    number: "",
                    name: ""
                },
                status: {
                    code: "",
                    name: ""
                },
                items: []
            });
        } else {
            $aatl_ib.ApiService.get("invoices/" + number,
                    {},
                    function (res, err) {
                        if (err) {
                            callback(null, {messages: ["Faild to get invoice detail: " + err]});

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
    save: function (invoice, callback) {

        let err = $aatl_ib.InvoiceService.validate(invoice);

        if (err.messages.length === 0) {
            $aatl_ib.ApiService.put("invoices",
                    invoice,
                    function (res, serverErr) {
                        if (serverErr) {

                            err.messages.push("Failed to save the client: " + serverErr);
                            callback(invoice, err);

                        } else if (res.status === "failure") {

                            err.messages.push(res.message);
                            callback(invoice, err);

                        } else if (res.status === "success") {

                            if (Array.isArray(res.warningMessages) && res.warningMessages.length > 0) {
                                err.warningMessages = res.warningMessages;
                            }

                            callback(JSON.parse(res.data), err);
                        } else {
                            callback(invoice, "Invalid response from server");
                        }
                    });

            return;
        }

        callback(invoice, err);
    },

    validate: function (invoice) {

        let err = {messages: []};

        if (invoice) {

            if ($aatl_ib.utils.isStringEmpty(invoice.date)) {
                err.messages.push("Please enter invoice date");
            } else if (!$aatl_ib.utils.isDate(invoice.date)) {
                err.messages.push("Please enter valid invoice date");
            }

            if ($aatl_ib.utils.isStringEmpty(invoice.client.number)) {
                err.messages.push("Please select client");
            }

            if($aatl_ib.utils.isStringEmpty(invoice.number) && invoice.items.length === 0){
                err.messages.push("Please add the invoice item before save");
            }
            
            invoice.items.forEach(item => {
                if (item.salesItem && $aatl_ib.utils.isStringEmpty(item.salesItem.code)) {
                    err.messages.push("Please select invoice detail item.");
                }
                
                if ($aatl_ib.utils.isStringEmpty(item.description)) {
                    err.messages.push("Please enter item description.");
                }
                
                if(isNaN(item.quantity) || item.quantity <= 0){
                    err.messages.push("Please enter valid item quantity.");
                }
                
                if(isNaN(item.rate) || item.rate <= 0){
                    err.messages.push("Please enter valid item rate.");
                }
            });
        }

        return err;
    }
};
