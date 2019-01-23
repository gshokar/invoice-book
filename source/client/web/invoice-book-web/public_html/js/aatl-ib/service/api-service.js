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

    baseUrl: "http://localhost:8080/invoicebookservice/api/",

    call: function (method, urlPath, data, callback) {

        let isCallback = $aatl_ib.utils.isFunction(callback);
        let url = this.getUrl(urlPath);

        let call = {
            url: url,
            headers: {sessionId: $aatl_ib.AuthService.getSessionId()},
            method: method,
            crossDomain: true,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: data
        };

        let request = $.ajax(call);

        request.done(function (res) {
            if (isCallback) {
                callback(res);
            }
        });

        request.fail(function (jqXHR, textStatus, errorThrown) {
            if (isCallback) {
                callback(null, textStatus);
            }
        });
    },

    post: function (urlPath, data, callback) {
        this.call("POST", urlPath, JSON.stringify(data), callback);
    },

    put: function (urlPath, data, callback) {
        this.call("PUT", urlPath, JSON.stringify(data), callback);
    },
    
    get: function (urlPath, data, callback) {
        this.call("GET", urlPath, data, callback);
    },

    getUrl: function (urlPath) {
        return this.baseUrl + urlPath;
    }
};
