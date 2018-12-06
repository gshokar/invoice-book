/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-28  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.ApiService = {

    createRequestData: function (type, dataType, data) {

        let request = {
            requestType: type,
            dataType: dataType,
            data: JSON.stringify(data)
        };

        if ($aatl_ib.AuthService.isLoggedIn()) {
            request.sessionId = $aatl_ib.AuthService.getSessionId();
        }

        return request;
    },

    post: function (type, dataType, data, callback) {

        let isCallback = $aatl_ib.utils.isFunction(callback);

        let requestData = this.createRequestData(type, dataType, data);

        let postCall = {
            url: "http://localhost:8080/invoicebookservice/service",
            method: "POST",
            crossDomain: true,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(requestData)
        };

        let request = $.ajax(postCall);

        request.done(function (res) {
            if (isCallback) {
                callback(res);
            }
        });

        request.fail(function (jqXHR, textStatus) {
            if (isCallback) {
                callback(null, textStatus);
            }
        });
    }
};
