/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-27  GShokar         Created
 * =============================================================================
 */


"use strict";

$aatl_ib.AuthService = {

    user: null,

    isLoggedIn: function () {
        return this.user !== undefined && this.user !== null;
    },

    getSessionId: function () {
        return this.user.sessionId;
    },

    login: function (loginId, password, callback) {

        if (!$aatl_ib.utils.isFunction(callback)) {
            console.log("$aatl_ib.service.AuthService.login(callback) - callback is not function");
            return;
        }

        $aatl_ib.ApiService.post("authenticate",
                {loginId: loginId, password: password},
                function (res, err) {
                    if (err) {
                        callback("Login request failed: " + err);

                    } else if(res.status === "failure"){
                        callback(res.message);
                    } else if(res.status === "success"){
                        $aatl_ib.AuthService.user = JSON.parse(res.data);
                        callback();
                        } else {
                            callback("Invalid response from server")
                    }
                });

    }
};